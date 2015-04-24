'use strict';

angular.module('contactmgrApp').controller(
        'LoginController',function($rootScope, $scope, $http, $location, LoginService) {
            
            var authenticate = function(credentials, callback) {
                var headers = credentials ? {
                    authorization : "Basic "
                            + btoa(credentials.username + ":"
                                    + credentials.password)
                } : {};
                console.log($scope.credentials.username);
            };
            var x =document.getElementById("notice");
            $scope.resetEmail = function(){
                $scope.notice = 'Your registered email';
                x.style.color = "Black";
                $scope.isHidden = false;
                $scope.email='';
            };
            $scope.sendEmail = function(){
                LoginService.checkEmail($scope.email).success(function(status) {
                    if(status === 200){
                        $scope.notice = 'The new password was sent to your email';
                        $scope.isHidden = false;
                        x.style.color = "Red";
                    }
                    if(status === 404){
                        $scope.notice = 'The email is not existed';
                    }
                });
            };
            $scope.checkLogin=function(){
                console.log($scope.credentials.username)
            }
        });