'use strict';

angular.module('contactmgrApp').service('ChangePasswordService', function($http) {
    this.changePassword = function (pass) {
            return $http.post("api/users/updatePassword",pass);
        };
    });