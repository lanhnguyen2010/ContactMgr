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
