'use strict';

angular.module('contactmgrApp')
    .service('LoginService', function($http) {
    	this.login = function (paraLogin) {
    		return $http.post("/api/authen/login",paraLogin);
        };
        this.checkEmail=function(email){
            return $http.get("/api/authen/checkmail",email);
        };
    });