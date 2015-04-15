'use strict';

angular.module('contactmgrApp').controller('UsersController',
		function($scope,UsersService, ngTableParams) {

	    	function init(){
	    		$scope.getCompanies();
	    		$scope.getRoles();
	    	};
			$scope.filter = {
				userName : '',
				firstlastName : '',
				email : '',
				role : '',
				createdFrom:'',
				createdTo:'',
			    assignedCompanies:''
			};
			var PAGE_SIZE = 10;
			var isFristSearchClicked = false;
			$scope.currentPage = 1;

	    	$scope.searchUsers = function (page) {
	    		isFristSearchClicked = true;
	    		if($scope.isLoading){
	    			return;
	    		}
	    		$scope.isLoading = true;
	    		
	    		UsersService.searchUsers($scope.filter, page, PAGE_SIZE)
	    		.success(function(data, status) {
	    			$scope.users = data['data'];
        			for (var i=0; i<$scope.users.length; i++) {
	        			if ($scope.users[i]['active'] == 1){
	        				$scope.users[i]['active'] = "Active";
	        			} else {
	        				$scope.users[i]['active'] = "Inactive";
	        			}
                    }
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
	    			$scope.currentPage = params.page();
	    			UsersService.searchUsers($scope.filter, params.page(), PAGE_SIZE)
	        		.success(function(data, status) {
	        			$scope.users = data['data'];
	        			for (var i=0; i<$scope.users.length; i++) {
		        			if ($scope.users[i]['active'] == 1){
		        				$scope.users[i]['active'] = "Active";
		        			} else {
		        				$scope.users[i]['active'] = "Inactive";
		        			}
	                    }
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
		    			$scope.searchUsers($scope.currentPage);
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
		    			$scope.searchUsers($scope.currentPage);
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
	    			console.log("Error get roles", status);
	    		});
	        };
	        $scope.getCompanies=function(){
	        	UsersService.getCompanies()
	        	.success(function(data,status){
	        		$scope.assignedcompanies=data;
	        	})
	        	.error(function (data, status) {
	    			console.log("Error get companies", status);
	    		});
	        }
	        init();      	    	
		})