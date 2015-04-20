'use strict';

angular.module('contactmgrApp')
    .controller('EditContactController', function($scope, $stateParams, ContactService) {
        var contactId = $stateParams.id;

        $scope.imageList = ['1.jpg', '2.jpg', '3.jpg', '4.jpg', '5.jpg', '6.jpg', '7.jpg', '8.jpg', '9.jpg', '10.jpg', '11.jpg', '12.jpg', '13.jpg', '14.jpg', '15.jpg'];

        $scope.uploadPhoto = function() {
            document.getElementById('imageUpload').click();
        };

        $scope.selectPhoto = function(imageURL) {
            var avatar = document.getElementById(typePhoto + contactId);
            avatar.src = imageURL; // address of img
            var avatar2 = document.getElementById(typePhoto + contactId + '_2');
            avatar2.src = imageURL; // address of img
            document.getElementById('closeButton').click();
        };

        $scope.selectUploadPhoto = function(imageUpload) {
            if (!confirm('Are you sure you want to upload this photo?')) {
                return;
            }

            var file = document.getElementById(imageUpload).files[0];
            var formData = new FormData();
            var aImage;
            formData.append('file', file);

            ContactService.uploadPhoto(contactId, formData)
                .success(function(data, status) {
                    aImage = data.pathFull;
                })
                .error(function(data, status) {
                });

            $scope.selectPhoto(aImage);
        };

        var typePhoto = 'avatar';
        $scope.getPhotos = function(type) {
            typePhoto = type;
            $scope.maxSize = 11;
            $scope.totalItems = 15;
            $scope.currentPage = 1;
            $scope.pageChanged();
        }

        $scope.images;
        $scope.pageChanged = function() {
            // TODO: this is for hard-code test
            $scope.images = $scope.imageList.slice( ($scope.currentPage - 1) * $scope.maxSize , $scope.currentPage * $scope.maxSize);
            /*
            var pageIndex = params.page();
            var pageSize = params.count();
            ContactService.getPhotos(contactId, pageIndex, pageSize)
                .success(function(data, status) {
                    params.total(data.totalItems);
                    $scope.images = data.items;
                    $defer.resolve(data.items);
                });
            */
        };
          $scope.contact;
          $scope.init = function() {
              ContactService.getViewContact(contactId).success(
                      function(data) {
                          $scope.contact = data;
                          if($scope.contact.work != null && $scope.contact.work.company != null){
                              $scope.selectedCompany = $scope.contact.work.company;
                          }
                      })
          };
        $scope.init();
        $scope.companies;
        
        $scope.selectedCompany = null;
        
        $scope.$watch('contact.work.companyId', function(result){
            for(var c in $scope.companies){
                if($scope.companies[c].id === $scope.contact.work.companyId){
                    $scope.contact.work.company = $scope.companies[c];
                    $scope.selectedCompany = $scope.companies[c];
                    break;
                }
            }
        });
        
        $scope.countries;
        $scope.validator;
        // save a contact
        $scope.saveContact = function() {
            if (typeof $scope.contact ==='undefined' || $scope.contact.id==null) {
                ContactService
                        .createContact($scope.contact)
                        .success(
                                function(data, status, headers,
                                        config) {
                                    // the contact is saved
                                    window.location = '#contact';
                                }).error(function(data, status, headers,config) {
                                    // has error
                                     $scope.validator = data.errors;
                                });
            } else {
                ContactService.updateContact($scope.contact.id,
                        $scope.contact).success(
                        function(data, status, headers, config) {
                            // the contact is saved
                            window.location = '#contact';
                        }).error(
                        function(data, status, headers, config) {
                            // has error
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
             return ($scope.selectedCompany != null);
         };
         
         $scope.saveCompany = function(){
             if($scope.hasSelectedCompany()){
                 if($scope.selectedCompany.id >0){
                    // Update a existing company
                     ContactService.updateCompany($scope.selectedCompany)
                     .success(function(data, status, headers, config) {
                         // close dialog
                         $('#companyInfoModal').modal('toggle');
                     })
                     .error(function(data, status, headers, config) {
                         // has error
                         console.log("Error: " + status);
                     });
                 } else{
                     ContactService.createCompany($scope.selectedCompany)
                         .success(function(data, status, headers, config) {
                             $scope.getCompanies();
                             // close dialog
                             $('#companyInfoModal').modal('toggle');
                         })
                         .error(function(data, status, headers, config) {
                             console.log("Error: " + status);
                             
                         });
                 }
                
             }
         };
         
         $scope.openDialogCreateCompany = function(){
             $scope.selectedCompany = null;
             $('#companyInfoModal').modal('show');
         };
         
         $scope.openDialogUpdateCompany = function(){
             $scope.selectedCompany = $scope.contact.work.company;
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
    });
