'use strict';

angular.module('contactmgrApp')
    .controller('UserController', function($scope) {

    	 $scope.data = {
                 username: '',
                 newPassword: '',
                 confirmNewPassword: '',
                 role: '',
                 firstName: '',
                 lastName: '',
                 expiredDate: '',
                 active: ''
             };
    });