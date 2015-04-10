'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, $http, ContactService, ngTableParams) {
    	var data = [{
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
    	
    	$scope.searchContacts=function(page){    		
    		if($scope.isLoading){
    			return;
    		}
    		if(!page){
    			$scope.contacts = [];
    			page = 0;
    		}
    		var PAGE_SIZE = 100;
    		$scope.page = page;
    		$scope.isLoading = true;
    		$http.get('http://localhost:8181/download/1?name=contact.json')
    		.success(function(items){
    			$scope.hasMoreContacts=(items.length >= PAGE_SIZE);
    			for (var i = 0; i < items.length; i ++) {
    				$scope.contacts.push(items[i]);
    			}
    			$scope.isLoading = false;
    		})
    	}
    	
    	$scope.contactsTableParams = new ngTableParams({
    		page: 1, // Show the first page
    		count: 10 // Count per page
    	}, {
    		counts: [],
    		total: data.length, // Length of data
    		getData: function($defer, params) {
    			$defer.resolve($scope.contacts = data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
    		}
    	});
    	
    	// Delete contacts
    	$scope.deleteContacts = function () {
    		ContactService.deleteContacts($scope.checkedIds)
    		.success(function (data, status) {
    			console.log(data, status);
    		})
    		.error(function (data, status) {
    			console.log(data, status);
    		});
    	};

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
            
            if ($scope.checkedIds.length > 0) {
            	$scope.checkedIds = $scope.checkedIds.substr(0, $scope.checkedIds.length - 1); 
            }
        }, true);
    });