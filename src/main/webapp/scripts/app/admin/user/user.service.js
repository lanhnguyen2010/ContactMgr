'use strict';

angular.module('contactmgrApp')
    .service('UsersService', function($http) {
    	this.deleteUsers = function (ids) {
    		return $http.delete("/api/users/?ids=" + ids, {});
    	};
    	this.activateUsers = function (ids) {
    		return $http.put("/api/users/active/?ids=" + ids, {});
    	};
    	this.inactivateUsers = function (ids) {
    		return $http.put("/api/users/deactive/?ids=" + ids, {});
    	};
    	this.searchUsers = function (criteria) {
    		return $http.post("/api/users/search",criteria);
    	};
    	this.getCompanies=function(){
    		return $http.get("/api/companies/names");
    	};
    	this.getRoles=function(){
    		return $http.get("/api/users/roles");
    	};
    })