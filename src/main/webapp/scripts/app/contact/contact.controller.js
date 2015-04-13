'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, ContactService, ngTableParams) {
    	
    	$scope.filter = {
    		name: '',
    		email:'',
    		mobile: '',
    		jobTitle: '',
    		department:'',
    		company:''
    	};
    	
    	var PAGE_SIZE = 10;
    	var isFristSearchClicked = false;
    	
    	$scope.searchContacts = function (page) {
    		isFristSearchClicked = true;
    		if($scope.isLoading){
    			return;
    		}
    		$scope.isLoading = true;
    		
    		ContactService.searchContacts($scope.filter, page, PAGE_SIZE)
    		.success(function(data, status) {
    			$scope.contacts = data['data'];
    			$scope.total = data['totalItem'];
    			$scope.isLoading = false;
    			$scope.contactsTableParams.reload();
    		})
    		.error(function(data, status) {
    			console.log(status);
    		});
    	}
    	
    	$scope.contactsTableParams = new ngTableParams({
    		page: 1, // Show the first page
    		count: 10, // Count per page
    	}, {
    		counts: [],
    		getData: function ($defer, params) {
    			if (!isFristSearchClicked)
    				return;
    			
    			ContactService.searchContacts($scope.filter, params.page(), PAGE_SIZE)
        		.success(function(data, status) {
        			$scope.contacts = data['data'];
        			params.total(data['totalItem']);
        			$defer.resolve($scope.contacts);
        		})
        		.error(function(data, status) {
        			console.log(status);
        		});
    			
    			$scope.checkboxes = {
    		        'checked': false, 
    		        items: {}
    		    };
    			$scope.checkedIds = '';
    		}
    	});
    	
    	$scope.selectedContact = {};
    	$scope.setSelectedContact = function(contact) {
    		$scope.selectedContact = contact;
    	};
    	
    	function findAndRemove(array, property, value) {
    		$.each(array, function(index, result) {
    			if(result[property] == value) {
    				array.splice(index, 1);
    		    }    
    		});
    	}
    	
    	// Delete contacts
    	$scope.deleteContacts = function () {
    		if (confirm("Do you want to delete?")) {
	    		ContactService.deleteContacts($scope.checkedIds)
	    		.success(function (data, status) {
	    			console.log("Deleted " + data + " contact(s)");
	    			$scope.contactsTableParams.reload();
	    		})
	    		.error(function (data, status) {
	    			console.log("Error", status);
	    		});
    		}
    	};

        $scope.checkboxes = {
    	    'checked': false, 
    	    items: {}
     	};

     	$scope.checkedIds = '';

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
    });

