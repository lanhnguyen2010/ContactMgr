'use strict';

angular.module('contactmgrApp').controller(
        'LoginController',function($rootScope, $scope, $http, $location, LoginService) {
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
            var x =document.getElementById("notice");
            $scope.resetEmail = function(){
                $scope.isLoading = false;
                $scope.btnSend = true;
                $scope.btnClose = false;
                $scope.notice = 'Your registered email';
                x.style.color = "Black";
                $scope.email='';
            };
            $scope.sendEmail = function(){
                x.style.color = "Black";
                $scope.notice = 'Sending email ...';
                $scope.isLoading = true;
                $scope.btnSend = false;
                $scope.btnClose = false;
                LoginService.checkEmail($scope.email).success(function(data, status) {
                     if (status == '200') {
                        $scope.isLoading = false;
                        $scope.btnSend = false;
                        $scope.btnClose = true;
                        $scope.notice = 'The new password was sent to your email';
                        x.style.color = "Black";
                    }
                }).error(function(data, status){
                     if (status == '404') {
                        $scope.isLoading = false;
                        $scope.btnSend = true;
                        $scope.btnClose = false;
                        $scope.notice = 'The email is not existed';
                        x.style.color = "Red";
                    }
                });
            };
            $scope.checkLogin=function(){
                console.log($scope.credentials.username)
            }
        });
