
'use strict';

angular.module('contactmgrApp')
    .service('ContactService', function($http) {

        this.deleteContacts = function(ids) {
            return $http.delete("/api/contacts?ids=" + ids, {});
        };

        this.searchContacts = function(critiaria) {
            return $http.post("/api/contacts/search", critiaria);
        };

        this.getCompanies = function() {
            return $http.get("/api/companies/names");
        }
        this.getAllCompanies = function() {
            return $http.get("/api/companies");
        }
        
        this.createContact = function (data) {
            return $http.post('/api/contacts', data);
        };
        this.updateContact = function (id,data) {
            return $http.put('/api/contacts/' + id, data);
        };
        this.getCountries = function() {
            return $http.get("/api/countries");
        };

        this.getViewContact = function(id) {
            return $http.get("/api/contacts/"+id);
        };
        this.updateCompany = function(company){
            var url = '/api/companies/' + company.id;
            console.log('Update company: ' + url)
            return $http.put(url, company);
        };
        
        this.createCompany = function(company){
            return $http.post('/api/companies', company);
        };        this.uploadPhoto = function(contactId, fileUpload) {
            return $http.post("/api/photos/"+contactId, fileUpload);
        };

        this.getPhotos = function(contactId, pageIndex, pageSize) {
            return $http.get("/api/photos/"+contactId, pageIndex, pageSize);
        };
        
        this.getCompanieById = function(id) {
            return $http.get("/api/companies/" + id);
        };
    });
