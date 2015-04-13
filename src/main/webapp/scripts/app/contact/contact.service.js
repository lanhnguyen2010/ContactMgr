'use strict';

angular.module('contactmgrApp')
    .service('ContactService', function($http) {
    	
    	this.deleteContacts = function (ids) {
    		return $http.delete("/api/contacts?ids=" + ids, {});
    	};
    	
    	this.searchContacts = function (filter, page, pageSize) {
    		return $http.post("/api/contacts/search?page=" + page + "&pageSize=" + pageSize, filter);
    	};
    });