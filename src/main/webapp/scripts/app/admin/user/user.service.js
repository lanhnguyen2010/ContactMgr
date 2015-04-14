'use strict';

angular.module('contactmgrApp')
    .service('UsersService', function($http) {
    	this.deleteUsers = function (ids) {
    		return $http.delete("/api/admin/user/?ids=" + ids, {});
    	};
    	this.activateUsers = function (ids) {
    		return $http.delete("/api/admin/user/?ids=" + ids, {});
    	};
    	this.inactivateUsers = function (ids) {
    		return $http.delete("/api/admin/user/?ids=" + ids, {});
    	};
    	this.searchUsers = function (filter, page, pageSize) {
    		return $http.post("/api/admin/user/search?page=" + page + "&pageSize=" + pageSize, filter);
    	};
    })