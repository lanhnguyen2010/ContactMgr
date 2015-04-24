'use strict';

angular.module('contactmgrApp')
    .service('LoginService', function($http) {
    	this.login = function (paraLogin) {
    		return $http.post("/api/authen/login",paraLogin);
        };
        this.checkEmail=function(email){
            return $http.put("/api/users/reset_password?email=" + email);
        };
    });