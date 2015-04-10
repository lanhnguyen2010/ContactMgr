'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, ContactService, ngTableParams) {
    	var dummyData = [{
    		id: 1,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 2,
    		name: 'Tran Van B',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 3,
    		name: 'Le Thi Hong Van',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 4,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 5,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 6,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 7,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 8,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 9,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 10,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 11,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}];
    	
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
    		count: PAGE_SIZE // Count per page
    	}, {
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

