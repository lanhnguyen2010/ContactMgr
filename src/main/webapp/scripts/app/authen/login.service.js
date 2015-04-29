'use strict';

angular.module('contactmgrApp').service('LoginService', function($http) {
    this.login = function (para) {
            return $http.post("/api/authenticate",para);
        };
        this.checkEmail=function(email){
            return $http.put("/api/users/reset_password?email=" + email);
        };
    });