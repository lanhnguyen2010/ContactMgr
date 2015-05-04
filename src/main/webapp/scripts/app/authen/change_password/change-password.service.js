'use strict';

angular.module('contactmgrApp').service('ChangePasswordService', function($http) {
    this.changePassword = function (pass) {
            return $http.put("api/users/updatePassword",pass);
        };
    });