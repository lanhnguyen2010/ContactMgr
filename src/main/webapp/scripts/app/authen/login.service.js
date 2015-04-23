'use strict';

angular.module('contactmgrApp').service('LoginService', function($http) {
    this.login = function (para) {
            return $http.post("/api/authenticate",para);
        };
    });