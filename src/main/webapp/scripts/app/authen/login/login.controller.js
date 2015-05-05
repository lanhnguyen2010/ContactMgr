'use strict';

angular
        .module('contactmgrApp')
        .controller(
                'LoginController',
                function($scope, $rootScope, $location, LoginService) {
                    $scope.isLogin = $rootScope.isLogin;
                    $scope.credentials = {
                        username : '',
                        password : ''
                    };
                    console.log($scope.isLogin);
                    if($scope.isLogin === true){
                        $location.path('/');
                    }
                    $rootScope.token = null;
                    $rootScope.reloadNavbar = false;
                    $scope.isLogin = false;
                    $scope.login = function() {
                        LoginService
                                .login($scope.credentials)
                                .success(
                                        function(data) {
                                            $scope.loginFailed = false;
                                            $rootScope.token = data.token;
                                            if (data.resetPasswordFlag === 'true') {
                                                $('#changePasswordModal').modal('toggle');
                                            } else {
                                                $location.path('/');
                                            }
                                            if (document
                                                    .getElementById('remember').checked) {
                                                document.cookie = $rootScope.token;
                                            }
                                            $rootScope.firstLogin = true;
                                            $rootScope.isLogin = true;
                                        }).error(function(data) {
                                    $scope.loginFailed = true;
                                });
                    }
                    var x = document.getElementById("notice");
                    $scope.resetEmail = function() {
                        $scope.isLoading = false;
                        $scope.btnSend = true;
                        $scope.btnClose = false;
                        $scope.notice= "Your registered email";
                        x.style.color = "Black";
                        $scope.email = '';
                    };
                    $scope.sendEmail = function() {
                        x.style.color = "Black";
                        $scope.notice= "Sending email ...";
                        $scope.isLoading = true;
                        $scope.btnSend = false;
                        $scope.btnClose = false;
                        LoginService
                                .checkEmail($scope.email)
                                .success(
                                        function(data, status) {
                                            if (status == '200') {
                                                $scope.isLoading = false;
                                                $scope.btnSend = false;
                                                $scope.btnClose = true;
                                                $scope.notice= data.message;
                                                x.style.color = "Black";
                                            }
                                        })
                                .error(
                                        function(data, status) {
                                            if (status == '400') {
                                                $scope.isLoading = false;
                                                $scope.btnSend = true;
                                                $scope.btnClose = false;
                                                $scope.notice= data.message;
                                                x.style.color = "Red";
                                            }
                                });
                    };
                });
