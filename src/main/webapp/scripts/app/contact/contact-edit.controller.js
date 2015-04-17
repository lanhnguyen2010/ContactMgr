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
            avatar.src = "../../../photos/" + imageURL; // address of img
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
                    aImage = data;
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
            console.log('Page changed to: ' + $scope.currentPage);
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
        function init(){
        	ContactService.getViewContact(contactId).success(function(data) {
        		  $scope.contact=data;
			})

		};
        init();
        $scope.companies;

        $scope.$watch('contact.work.companyId', function(result){
            for(var c in $scope.companies){
                if($scope.companies[c].id == $scope.contact.work.companyId){
                    $scope.contact.work.company.message = $scope.companies[c].message;
                    console.log( $scope.contact.work.company);
                    break;
                }
            }
        });

        $scope.countries;


        // save a contact
        $scope.saveContact = function(){
            console.log("Contact: " + $scope.contact.firstName);
            ContactService.createContact($scope.contact)
                .success(function(data, status, headers, config) {
                    // the contact is saved
                    console.log("Saved contact!");
                }).error(function(data, status, headers, config) {
	            // has error
	                console.log("Error: " + status);
	            });
         };

        $scope.getCompanies = function(){
             ContactService.getCompanies()
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
        $scope.viewContact=function(){
        	 ContactService.getViewContact(1).success(function(data){
        		 console.log("Error: " + data.firstName);
        		 //$scope.contact=data;
        	 });
        }
        $scope.getContactInfo = function(id){

        };
    });
