'use strict';

angular.module('contactmgrApp')
    .service('LoginService', function($http) {
        this.login = function (para) {
            var config = {
                    method: 'POST',
                    url: '/api/authenticate',
                    headers : {
                        'X-Auth-Username' : para.username,
                        'X-Auth-Password' : para.password
                    }
                }
            return $http(config);
        };
        this.checkEmail=function(email){
            return $http.put("/api/users/reset_password?email=" + email);
        };
    });