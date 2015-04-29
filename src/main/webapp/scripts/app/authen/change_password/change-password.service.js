'use strict';

angular.module('contactmgrApp').service('ChangePasswordService', function($http) {
    this.login = function (pass) {
            return $http.post("/api/users/updatePassword",pass);
        };
    });