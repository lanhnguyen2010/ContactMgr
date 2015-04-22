'use strict';

angular.module('contactmgrApp')
    .controller('EditContactController', function($scope, $stateParams, ContactService) {
        var contactId = $stateParams.id;

        $scope.imageList = [];

        $scope.uploadPhoto = function() {
            document.getElementById('imageUpload').click();
        };

        $scope.selectPhoto = function(imageURL) {
            if(typePhoto == 'avatar'){
                $scope.contact.photo = "/api/photos/" + imageURL.pathFull;
            } else {
                $scope.selectedCompany.logo = "/api/photos/" + imageURL.pathFull;
            }
            document.getElementById('closeButton').click();
        };

        $scope.selectUploadPhoto = function(imageUpload) {
            if (!confirm('Are you sure to want to upload this photo?')) {
                return;
            }

            var file = document.getElementById(imageUpload).files[0];
            var formData = new FormData();
            formData.append('file', file);

            ContactService.uploadPhoto(formData)
                .success(function(data, status) {
                	$scope.selectPhoto(data);
                })
                .error(function(data, status) {
                	if (status == '412') {
                	    alert('invalid photo type!');
                        document.getElementById('closeButton').click();
                    }
                });

        };

        var typePhoto = 'avatar';
        $scope.getPhotos = function(type) {
            typePhoto = type;
            $scope.maxSize = 11;
            $scope.totalItems = 15;
            $scope.currentPage = 1;
            $scope.pageChanged();
        }

        $scope.pageChanged = function() {
            ContactService.getPhotos($scope.currentPage,$scope.maxSize)
                .success(function(data, status) {
                    $scope.totalItems = data['totalItems'];
                    $scope.imageList = data['items'];
                })
                .error(function(data, status) {
                    console.log(status);
                });
        };

        $scope.contact;
        $scope.init = function() {
            ContactService.getViewContact(contactId).success(
                    function(data) {
                        $scope.contact = data;
                        if($scope.contact.work != null && $scope.contact.work.company != null){
                            //$scope.selectedCompany = $.extend(true, {}, $scope.contact.work.company);
                        }
                    })
          };
        $scope.init();
        $scope.companies;

        $scope.selectedCompany = null;
        $scope.companyValidator = null;

        $scope.$watch('contact.work.companyId', function(result){
            for(var c in $scope.companies){
                if($scope.companies[c].id === $scope.contact.work.companyId){
                    $scope.contact.work.company = $scope.companies[c];
                    break;
                }
            }
        });

        $scope.countries;
        $scope.validator;
        // save a contact
        $scope.saveContact = function() {
            var copyContact = $.extend(true, {}, $scope.contact);
            if (typeof $scope.contact ==='undefined' || $scope.contact.id==null) {
                ContactService
                        .createContact(copyContact)
                        .success(
                                function(data, status, headers, config) {
                                    window.location = '#contact';
                                }).error(function(data, status, headers,config) {
                                     $scope.validator = data.errors;
                                });
            } else {
                ContactService.updateContact($scope.contact.id, copyContact)
                    .success(
                        function(data, status, headers, config) {
                            window.location = '#contact';
                        }).error(
                        function(data, status, headers, config) {
                            $scope.validator = data.errors;
                        });
            }
        };
         $scope.getCompanies = function(){
             ContactService.getAllCompanies()
                 .success(function(data, status, headers, config) {
                     $scope.companies = data;
                 });
         };
         $scope.getCompanies();

         $scope.getCountries = function(){
             ContactService.getCountries()
                 .success(function(data, status, headers, config) {
                 $scope.countries = data;
             });
         }
         $scope.getCountries();

         $scope.hasSelectedCompany = function(){
             return ($scope.contact.work != null && $scope.contact.work.companyId > 0);
         };

         $scope.saveCompany = function(){
             if($scope.selectedCompany != null){
                 if($scope.selectedCompany.id >0){
                     ContactService.updateCompany($scope.selectedCompany)
                     .success(function(data, status, headers, config) {
                         $scope.contact.work.company = data;
                         $scope.selectedCompany = null;
                         $scope.getCompanies();
                         $scope.companyValidator = null;
                         $('#companyInfoModal').modal('toggle');
                     })
                     .error(function(data, status, headers, config) {
                    	 $scope.companyValidator = data.errors;
                     });
                 } else{
                     ContactService.createCompany($scope.selectedCompany)
                         .success(function(data, status, headers, config) {
                             $scope.contact.work.company = data;
                             $scope.selectedCompany = null;
                             $scope.contact.work.companyId = data.id;
                             $scope.getCompanies();
                             $scope.companyValidator = null;
                             $('#companyInfoModal').modal('toggle');
                         })
                         .error(function(data, status, headers, config) {
                             $scope.companyValidator = data.errors;

                         });
                 }

             }
         };

         $scope.cancelEditCompany = function(){
        	 $scope.selectedCompany = null;
        	 $scope.companyValidator = null;
             $('#companyInfoModal').modal('hide');
         };

         $scope.openDialogCreateCompany = function(){
        	 $scope.companyValidator = null;
             $scope.selectedCompany = null;
             $('#companyInfoModal').modal('show');
         };

         $scope.openDialogUpdateCompany = function(){
        	 $scope.companyValidator = null;
             $scope.selectedCompany = $scope.selectedCompany = $.extend(true, {}, $scope.contact.work.company);
             $('#companyInfoModal').modal('show');
         };

         $scope.getTitleOfCompanyDialog = function(){
             if($scope.hasSelectedCompany()){
                 return 'contact-edit.work.edit-company';
             } else {
                 return 'contact-edit.work.create-company'
             }
         };
         $scope.postCode;
         $scope.isNumeric=function(evt){
             var theEvent = evt||window.event;
             var key = theEvent.keyCode || theEvent.which;
             key = String.fromCharCode(key);
             var regex = /[0-9]/;
             if (!regex.test(key)) {
                 theEvent.returnValue = false;
                 if (theEvent.preventDefault) theEvent.preventDefault();
             }
         };

         $scope.getAvatar = function(){
             if($scope.contact == null){
                 $scope.contact = {photo: '../../../photos/unknown.jpg'};
                 return "../../../photos/unknown.jpg";
             }
             return $scope.contact.photo;
         };

         $scope.getLogo = function(){
             if($scope.contact == null){
                 $scope.contact = {work: { company: {logo: '../../../photos/missing-logo.jpg'}}};
                 return "../../../photos/missing-logo.jpg";
             } else if($scope.contact.work == null){
                 $scope.contact.work = { company: {logo: '../../../photos/missing-logo.jpg'}};
                 return "../../../photos/missing-logo.jpg";
             } else if($scope.contact.work.company == null){
                 $scope.contact.work.company = {logo: '../../../photos/missing-logo.jpg'};
                 return "../../../photos/missing-logo.jpg";
             }
             return $scope.contact.work.company.logo;
         };

         $scope.getLogoOfSelectedCompany = function(){
             if($scope.selectedCompany == null){
                 $scope.selectedCompany = {logo: '../../../photos/missing-logo.jpg'};
                 return "../../../photos/missing-logo.jpg";
             }
             return $scope.selectedCompany.logo;
         };
    });
