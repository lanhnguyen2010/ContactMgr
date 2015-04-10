'use strict';

angular.module('contactmgrApp')
    .service('ContactService', function($http) {

    	this.deleteContacts = function (ids) {
    		return $http.delete("/api/contacts?ids=" + ids, {});
    	};

    	this.searchContacts = function (filter, page, pageSize) {
    		var jsonString = JSON.stringify({contact: filter, page: page, pagesize: pageSize});
    		return $http.post("/api/contacts/search?data=" + jsonString);
    	};
    });
