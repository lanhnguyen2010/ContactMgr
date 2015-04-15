'use strict';

angular.module('contactmgrApp')
    .controller('UserController', function($scope) {
    	 $scope.user = {
                 username: '',
                 newPassword: '',
                 confirmNewPassword: '',
                 assignCompany:'',
                 role: '',
                 firstName: '',
                 lastName: '',
                 expiredDate: '',
                 active: ''
             };
    }).controller('DateController', ['$scope', function($scope) {
        $scope.value = new Date();
        $scope.saveUser=function(){
        	console.log("User" + $scope.user.username);
        	UserService.saveUser($scope.user)
        }
    }]);