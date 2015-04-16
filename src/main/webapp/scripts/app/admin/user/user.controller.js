'use strict';

angular.module('contactmgrApp')
.controller('HomeController', function($scope) {
	$scope.user;
	$scope.companies;
	$scope.roles;
	$scope.saveUser =function(){
		console.log("User: "+ $scope.user.username);
		UserService.saveUser($scope.user)
			.success(function(data,status,header,config){
				console.log("Saved user!");
			}).error(function(data,status,header,config){
				console.log("Error: " + status);
			});
	};
	$scope.getRoles = function(){
		UserService.getRoles().success(function(data,status,header,config){
			$scope.roles = data;
		});
	};
	$scope.getRoles();
	
	$scope.getCompanies = function(){
        ContactService.getCompanies()
            .success(function(data, status, headers, config) {
                $scope.companies = data;
            });
    };
    $scope.getCompanies();
});