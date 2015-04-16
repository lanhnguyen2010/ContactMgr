'use strict';

angular.module('contactmgrApp')
    .controller('EditContactController', function($scope, $stateParams, ContactService, ngTableParams) {
    	var contactId = $stateParams.id;

    	$scope.images = ['1.jpg', '2.jpg', '3.jpg', '4.jpg', '5.jpg', '6.jpg', '7.jpg', '8.jpg', '9.jpg', '10.jpg', '11.jpg', '12.jpg', '13.jpg', '14.jpg', '15.jpg'];

        $scope.uploadPhoto = function() {
            document.getElementById('imageUpload').click();
        };

        $scope.selectPhoto = function(imageURL) {
            var avatar = document.getElementById('avatar' + contactId);
            avatar.src = "../../../photos/" + imageURL; // address of img
            document.getElementById('closeButton').click();
        };

        $scope.selectUploadPhoto = function() {
            if (!confirm('Are you sure you want to upload this photo?')) {
                return;
            }
            $scope.images.push('16.jpg');
            $scope.selectPhoto('16.jpg');
        };

        $scope.imageParams = new ngTableParams({
            page: 1,
            count: 11
        },{
            counts: [],
            total: $scope.images.length,
            getData: function($defer,params){
                $scope.imagesPage = $scope.images.slice( (params.page() - 1) * params.count() , params.page() * params.count())
                $defer.resolve($scope.images);
            }
        });

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
