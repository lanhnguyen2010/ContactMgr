'use strict';

angular.module('contactmgrApp').controller(
        'ChangePasswordController',
        function($scope, ChangePasswordService) {
            $scope.resetInfor = function() {
                $scope.pass = {
                    oldPassword : '',
                    password : '',
                    passwordConfirm : ''
                };
                $scope.validator = null;
            }
            $scope.changePassword = function() {
                ChangePasswordService.changePassword($scope.pass).success(
                        function(data, status, headers, config) {
                            window.alert("Change password successful!");
                            $('#changePasswordModal').modal('toggle');
                            $scope.resetInfor();
                        }).error(function(data, status, header, config) {
                    $scope.validator = data.errors;
                    window.alert("Cannot change password!");
                });
            }
        });