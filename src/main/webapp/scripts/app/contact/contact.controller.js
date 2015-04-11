'use strict';

angular.module('contactmgrApp')

    	
    	$scope.filter = {
    		name: '',
    		mobile: '',
    		jobTitle: '',
    		department:'',
    		email:'',
    		company:'',
    		page:''
    	};
    	
    	var PAGE_SIZE = 10;
    	$scope.currentPage = 1;
    	//$scope.total = dummyData.length; // For dummy data
    	$scope.searchContacts = function (isPaging) {
    		if($scope.isLoading){
    			return;
    		}
    		
    		$scope.isLoading = true;
    		
    		ContactService.searchContacts($scope.filter, $scope.currentPage, PAGE_SIZE)
    		.success(function(data, status) {
    			$scope.contacts = data['contact'];
    			$scope.total = data['total'];
    			$scope.isLoading = false;
    			
    			if (!isPaging) {
    				$scope.contactsTableParams.reload();
    			}
    		})
    		.error(function(data, status) {
    			console.log(status);
    		});
    	}
    	
    	$scope.contactsTableParams = new ngTableParams({
    		page: 1, // Show the first page
    		count: 10 // Count per page
    		count: PAGE_SIZE // Count per page
    	}, {
    		total: data.length, // Length of data
    		getData: function($defer, params) {
    			$defer.resolve($scope.contacts = data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
    		counts: [],
    		total: dummyData.length, // For dummy data
    		//total: $scope.total, // For real data
    		getData: function ($defer, params) {
    			$scope.currentPage = params.page();
    			$defer.resolve($scope.contacts); // For real data
    			//$defer.resolve($scope.contacts = dummyData.slice((params.page() - 1) * params.count(), params.page() * params.count())); // For dummy data
    			$scope.searchContacts(true);
    			
    			$scope.checkboxes = {
    		        'checked': false, 
    		        items: {}
    		    };
    			
    			$scope.checkedIds = '';
    		}
    	});

    	var inArray = Array.prototype.indexOf 
    	? function (val, arr) {
    		return arr.indexOf
    	} 
    	: function (val, arr) {
    		var i = arr.length; 

    		while (i--) {
    			if (arr[i] === val) {
    				return i;
    			}
    	
    	$scope.selectedContact = {};
    	$scope.setSelectedContact = function(contact) {
    		$scope.selectedContact = contact;
    	};
    	
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

    		return -1;
    	};

    	$scope.names = function(column) {
            var def = $q.defer(),
                arr = [],
                names = [];

            angular.forEach(data, function (item) {
                if (inArray(item.name, arr) === -1) {
                    arr.push(item.name);
                    names.push({
                        'id': item.name,
                        'title': item.name
                    });
                }
            });

            def.resolve(names);

            return def;
        };

        $scope.checkboxes = {
    	    'checked': false, 
    	    items: {}
     	};

     	$scope.checkedIds = [];
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
            $scope.checkedIds = [];
            $scope.checkedIds = '';
            for (var item in $scope.checkboxes.items) {
            	if ($scope.checkboxes.items[item]) {
            		$scope.checkedIds.push(parseInt(item));
            		$scope.checkedIds = $scope.checkedIds + item + ',';
            	}
            }
            
            // Remove the final ','
            if ($scope.checkedIds.length > 0) {
            	$scope.checkedIds = $scope.checkedIds.substr(0, $scope.checkedIds.length - 1); 
            }
        }, true);
    });

