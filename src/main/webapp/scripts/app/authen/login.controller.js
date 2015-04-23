'use strict';

angular.module('contactmgrApp').controller(
        'LoginController',function($rootScope, $scope, $http, $location) {
            
            var authenticate = function(credentials, callback) {
                var headers = credentials ? {
                    authorization : "Basic "
                            + btoa(credentials.username + ":"
                                    + credentials.password)
                } : {};
                console.log($scope.credentials.username);
            };
            $scope.notice = 'Your registered email';
            $scope.resetEmail = function(){
                $scope.email=''
            };
            $scope.checkLogin=function(){
                console.log($scope.credentials.username)
            }
        });