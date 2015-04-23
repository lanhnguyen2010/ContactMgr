'use strict';

angular.module('contactmgrApp').controller(
        'LoginController',
        function($scope,LoginService) {
            $scope.credentials = {
                    username: '',
                    password:''
            };
            $scope.para = btoa($scope.credentials.username + ":" + $scope.credentials.password);
            console.log($scope.para);
            LoginService.login($scope.para).success(function(data) {
                console.log($scope.para);
                if (data.name) {
                    console.log('success');
                    $scope.authenticated = false;
                } else {
                    console.log('success 11111');
                }
            }).error(function() {
                $scope.authenticated = false;
            });
            
        });