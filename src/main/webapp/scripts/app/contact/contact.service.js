'use strict';

angular.module('contactmgrApp')
    .service('ContactService', function($http) {
    	
    	this.deleteContacts = function (ids) {
    		return $http.delete('/api/contacts?contactIds=' + ids, {});
    	};
    	
    	this.searchContacts = function (filter, page, pageSize) {
    		var jsonString = JSON.stringify({contact: filter, page: page, pageSize: pageSize});
    		return $http.post('/api/contacts/search?data=' + jsonString, {});
    	};
    	
    	this.getCompanies = function () {
    		return $http.get('/api/companies/names');
    	};
    });