'use strict';

angular.module('contactmgrApp')
    .service('ContactService', function($http, MULTI_DELETE_CONTACT_API, SEARCH_CONTACT_API) {
    	
    	this.deleteContacts = function (ids) {
    		return $http.delete(MULTI_DELETE_CONTACT_API + ids, {});
    	};
    	
    	this.searchContacts = function (filter, page, pageSize) {
    		return $http.post(SEARCH_CONTACT_API, {filter: filter, page: page, pagesize: pageSize});
    	};
    });