'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, ContactService, ngTableParams) {
    	var refresh = true; // Do not search when accessing page the first time
    	
    	var resetCheckboxes = function () {
        	$scope.checkboxes = {
	            'checked': false,
	            items: {}
            };

            $scope.checkedIds = '';
        };
        
        resetCheckboxes();
        
        // Get companies to fill select box
        $scope.companies = [];
        ContactService.getCompanies()
        .success(function (data, status) {
        	$scope.companies = data;
        })
        .error(function (data, status) {
        	console.log('Error', status);
        });
    	
        $scope.filter = {
            name: '',
            mobile: '',
            jobTitle: '',
            department:'',
            email:'',
            company:''
        };
        
        var PAGE_SIZE = 10;
        $scope.contacts = [];
        $scope.total = 0;
        $scope.searchContacts = function () {
        	$scope.contactsTableParams.reload();
        }
        
        $scope.contactsTableParams = new ngTableParams({
            page: 1, // Show the first page
            count: PAGE_SIZE // Count per page
        }, {
            counts: [],
            total: $scope.total,
            getData: function ($defer, params) {
            	if (!refresh) {
            		ContactService.searchContacts($scope.filter, params.page(), PAGE_SIZE)
                    .success(function(data, status) {
                        $scope.contacts = data['contact'];
                        $scope.total = data['total'];
                        
                        params.total($scope.total);
                        $defer.resolve($scope.contacts);
                        
                        resetCheckboxes();
                    })
                    .error(function(data, status) {
                        console.log('Error', status);
                    });
            	} else {
            		refresh = false;	
            	}
            }
        });        

        // Delete contacts
        $scope.deleteContacts = function () {
            if (confirm("Do you want to delete?")) {
                ContactService.deleteContacts($scope.checkedIds)
                .success(function (data, status) {
                    $scope.searchContacts();
                })
                .error(function (data, status) {
                    console.log("Error", status);
                });
            }
        };        

        // watch for check all checkbox
        $scope.$watch('checkboxes.checked', function(value) {
            angular.forEach($scope.contacts, function(item) {
                if (angular.isDefined(item.id)) {
                    $scope.checkboxes.items[item.id] = value;
                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function(values) {
            if (!$scope.contacts) {
                return;
            }

            var checked = 0, unchecked = 0,
                total = $scope.contacts.length;

            angular.forEach($scope.contacts, function(item) {
                checked += ($scope.checkboxes.items[item.id]) || 0;
                unchecked += (!$scope.checkboxes.items[item.id]) || 0;
            });

            if ((unchecked == 0) || (checked == 0)) {
                $scope.checkboxes.checked = (checked == total);
            }

            // grayed checkbox
            angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));

            // Create checked id list
            $scope.checkedIds = '';
            for (var item in $scope.checkboxes.items) {
                if ($scope.checkboxes.items[item]) {
                    $scope.checkedIds = $scope.checkedIds + item + ',';
                }
            }

            // Remove the final ','
            if ($scope.checkedIds.length > 0) {
                $scope.checkedIds = $scope.checkedIds.substr(0, $scope.checkedIds.length - 1);
            }
        }, true);
        
        // Show company info into popup
        $scope.selectedContact = {};
        $scope.setSelectedContact = function(contact) {
            $scope.selectedContact = contact;            
        };
    });