'use strict';

angular.module('contactmgrApp').controller(
        'LoginController',
        function($scope, LoginService) {
            $scope.reset = function() {
                $scope.credentials = {
                    username : '',
                    password : ''
                };
            }
            $scope.login = function() {
                console.log($scope.credentials);
                LoginService.login($scope.credentials).success(
                        function(data, status, headers, config) {
                            $scope.authenticated = false;
                        }).error(function(data, status, header, config) {
                    $scope.authenticated = true;
                });
            }
        });