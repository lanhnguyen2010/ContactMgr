'use strict';

angular
        .module('contactmgrApp')
        .controller(
                'LoginController',
                function($scope, $rootScope, $location, LoginService) {
                    $scope.reset = function() {
                        $scope.credentials = {
                            username : '',
                            password : ''
                        };
                    }
                    $rootScope.reloadNavbar = false;
                    $scope.login = function() {
                        LoginService
                                .login($scope.credentials)
                                .success(
                                        function(data) {
                                            $scope.loginFailed = false;
                                            $rootScope.token = data.token;
                                            $location.path('/');
                                            if (document
                                                    .getElementById('remember').checked) {
                                                $scope.maintainLogin();
                                            }
                                            $rootScope.firstLogin = true;
                                        }).error(function(data) {
                                    $scope.loginFailed = true;
                                });
                    }
                    var x = document.getElementById("notice");
                    $scope.resetEmail = function() {
                        $scope.isLoading = false;
                        $scope.btnSend = true;
                        $scope.btnClose = false;
                        $scope.registeredEmail = true;
                        $scope.existedEmail = false;
                        $scope.sendingEmail = false;
                        $scope.newPass = false;
                        x.style.color = "Black";
                        $scope.email = '';
                    };
                    $scope.sendEmail = function() {
                        x.style.color = "Black";
                        $scope.registeredEmail = false;
                        $scope.existedEmail = false;
                        $scope.sendingEmail = true;
                        $scope.newPass = false;
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
                                                $scope.registeredEmail = false;
                                                $scope.existedEmail = false;
                                                $scope.sendingEmail = false;
                                                $scope.newPass = true;
                                                x.style.color = "Black";
                                            }
                                        })
                                .error(
                                        function(data, status) {
                                            if (status == '400') {
                                                $scope.isLoading = false;
                                                $scope.btnSend = true;
                                                $scope.btnClose = false;
                                                $scope.registeredEmail = false;
                                                $scope.existedEmail = true;
                                                $scope.sendingEmail = false;
                                                $scope.newPass = false;
                                                x.style.color = "Red";
                                            }
                                        });
                    };
                });
