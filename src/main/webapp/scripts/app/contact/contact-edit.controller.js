'use strict';

angular
        .module('contactmgrApp')
        .controller(
                'EditContactController',
                function($scope, $stateParams, ContactService) {
                    var contactId = $stateParams.id;
                    $scope.contact;
                    function init() {
                        ContactService.getViewContact(contactId).success(
                                function(data) {
                                    $scope.contact = data;
                                })
                    }
                    ;
                    init();
                    $scope.companies;

                    $scope
                            .$watch(
                                    'contact.work.companyId',
                                    function(result) {
                                        for ( var c in $scope.companies) {
                                            if ($scope.companies[c].id == $scope.contact.work.companyId) {
                                                $scope.contact.work.company.message = $scope.companies[c].message;
                                                console
                                                        .log($scope.contact.work.company);
                                                break;
                                            }
                                        }
                                    });
                    $scope.countries;
                    $scope.validator;
                    // save a contact
                    $scope.saveContact = function() {
                        console.log("Contact: " + $scope.contact);
                        if (typeof $scope.contact ==='undefined' || $scope.contact.id==null) {
                            ContactService
                                    .createContact($scope.contact)
                                    .success(
                                            function(data, status, headers,
                                                    config) {
                                                // the contact is saved
                                                console.log("Saved contact!"
                                                        + status);
                                            }).error(function(data, status, headers,config) {
                                                // has error
                                            	 $scope.validator = data.errors;
                                            });
                        } else {
                            ContactService.updateContact($scope.contact.id,
                                    $scope.contact).success(
                                    function(data, status, headers, config) {
                                        // the contact is saved
                                        console.log("Update contact!");
                                    }).error(
                                    function(data, status, headers, config) {
                                        // has error
                                    	$scope.validator = data.errors;
                                        console.log("Error Update: " + status);
                                    });
                        }
                    };
                    $scope.getCompanies = function() {
                        ContactService.getCompanies().success(
                                function(data, status, headers, config) {
                                    $scope.companies = data;
                                });
                    };
                    $scope.getCompanies();

                    $scope.getCountries = function() {
                        ContactService.getCountries().success(
                                function(data, status, headers, config) {
                                    $scope.countries = data;
                                });
                    }
                    $scope.getCountries();
                    $scope.viewContact = function() {
                        ContactService.getViewContact(1).success(
                                function(data) {
                                    console.log("Error: " + data.firstName);
                                    // $scope.contact=data;
                                });

                    }
                    $scope.getContactInfo = function(id) {

                    };
                });