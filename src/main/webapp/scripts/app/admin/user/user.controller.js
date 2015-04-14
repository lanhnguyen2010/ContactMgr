'use strict';

angular.module('contactmgrApp').controller('UsersController',
		function($scope,UsersService, ngTableParams) {

			var dummy = [ 
			              {
				id : '1',
				username : 'Lanhnguyen',
				firstName : 'Lanh',
				lastName : 'Nguyen',
				email : 'lanhnguyen@kms',
				role : 'admin'
			} 
			              ]
	    	function init(){
	    		$scope.getRoles();
	    	};
			$scope.filter = {
				firstName : '',
				lastName : '',
				email : '',
				role : '',
			};
			var PAGE_SIZE = 10;
			var isFristSearchClicked = false;

	    	$scope.searchUsers = function (page) {
	    		isFristSearchClicked = true;
	    		if($scope.isLoading){
	    			return;
	    		}
	    		$scope.isLoading = true;
	    		
	    		UsersService.searchUsers($scope.filter, page, PAGE_SIZE)
	    		.success(function(data, status) {
	    			$scope.users = data['data'];
	    			$scope.total = data['totalItem'];
	    			$scope.isLoading = false;
	    			$scope.usersTableParams.reload();
	    		})
	    		.error(function(data, status) {
	    			console.log(status);
	    		});
	    	}
	    	$scope.usersTableParams = new ngTableParams({
	    		page: 1, // Show the first page
	    		count: 10, // Count per page
	    	}, {
	    		counts: [],
	    		getData: function ($defer, params) {
	    			if (!isFristSearchClicked)
	    				return;
	    			UsersService.searchUsers($scope.filter, params.page(), PAGE_SIZE)
	        		.success(function(data, status) {
	        			$scope.users = dummy;
	        			
	        			console.log($scope.users)
	        			params.total(data['totalItem']);
	        			$defer.resolve($scope.users);
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
	    	//select controller
	    	$scope.selectedUser = {};
	    	$scope.setSelectedUser = function(user) {
	    		$scope.selectedUser = user;
	    	};
	    	
	    	function findAndRemove(array, property, value) {
	    		$.each(array, function(index, result) {
	    			if(result[property] == value) {
	    				array.splice(index, 1);
	    		    }    
	    		});
	    	}
	    	
	    	//delete Users Controller
	    	$scope.deleteUsers = function () {
	    		if (confirm("Do you want to delete?")) {
		    		UsersService.deleteUsers($scope.checkedIds)
		    		.success(function (data, status) {
		    			console.log("Deleted " + data + " user(s)");
		    			$scope.usersTableParams.reload();
		    		})
		    		.error(function (data, status) {
		    			console.log("Error", status);
		    		});
	    		}
	    	};
	    	
	    	//activate Users Controller
	    	$scope.activateUsers = function () {
	    		if (confirm("Do you want to Activate?")) {
		    		UsersService.activateUsers($scope.checkedIds)
		    		.success(function (data, status) {
		    			console.log("Avtivate " + data + " user(s)");
		    			$scope.usersTableParams.reload();
		    		})
		    		.error(function (data, status) {
		    			console.log("Error", status);
		    		});
	    		}
	    	};
	    	//inactivate users Controller
	    	$scope.inactivateUsers = function () {
	    		if (confirm("Do you want to Inactivate?")) {
		    		UsersService.inactivateUsers($scope.checkedIds)
		    		.success(function (data, status) {
		    			console.log("Inavtivate " + data + " user(s)");
		    			$scope.usersTableParams.reload();
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
	            angular.forEach($scope.users, function(item) {
	                if (angular.isDefined(item.id)) {
	                    $scope.checkboxes.items[item.id] = value;
	                }
	            });
	        });

	        // watch for data checkboxes
	        $scope.$watch('checkboxes.items', function(values) {
	            if (!$scope.users) {
	                return;
	            }

	            var checked = 0, unchecked = 0,
	                total = $scope.users.length;

	            angular.forEach($scope.users, function(item) {
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
	        
	        $scope.getRoles=function(){
	        	UsersService.getRoles()
	        	.success(function(data,status){
	        		$scope.roles=data;
	        	})
	        	.error(function (data, status) {
	    			console.log("Error get companies", status);
	    		});
	        };
	        	      	    	
		})