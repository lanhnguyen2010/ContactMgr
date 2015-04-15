'use strict';

angular.module('contactmgrApp')
    .controller('EditContactController', function($scope, ContactService) {
        $scope.contact;
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
    });