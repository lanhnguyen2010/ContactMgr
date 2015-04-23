
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
           // only send companyId to service
           if(data.work != null && data.work.company != null){
           	data.work.company = null;
           }
           
           var data1 = this.replaceEmptyDataWithNullOfContact(data);
            return $http.post('/api/contacts', data1);
        };
        
        this.updateContact = function (id,data) {
           // only send companyId to service
           if(data.work != null && data.work.company != null){
           	data.work.company = null;
           }
           var data1 = this.replaceEmptyDataWithNullOfContact(data);
            return $http.put('/api/contacts/' + id, data1);
        };
        
        this.replaceEmptyDataWithNullOfContact = function(contact){
           if(contact.email == ''){
           	contact.email = null;
           }
           if(contact.mobile == ''){
           	contact.mobile = null;
           }
           if(contact.home != null){
           	if(contact.home.address != null){
               if(contact.home.address.postalCode == ''){
                   	contact.home.address.postalCode = null;
                   }
                   if(contact.home.address.country == ''){
                   	contact.home.address.country = null;
                   }
               
           	}
               if(contact.home.phone == ''){
               	contact.home.phone = null;
               }
               
               if(contact.home.fax == ''){
               	contact.home.fax = null;
               }
           }
           
           return contact;
        };
        
        this.getCountries = function() {
            return $http.get("/api/countries");
        };

        this.getViewContact = function(id) {
            return $http.get("/api/contacts/" + id);
        };
        this.updateCompany = function(company){
           this.replaceEmptyDataWithNullOfCompany(company);
            var url = '/api/companies/' + company.id;
            console.log('Update company: ' + url)
            return $http.put(url, company);
        };
        
        this.createCompany = function(company){
            this.replaceEmptyDataWithNullOfCompany(company);
            return $http.post('/api/companies', company);
        };        
        
        this.uploadPhoto = function(fileUpload) {
           var config = {headers: {'Content-Type': undefined}};
            return $http.post("/api/photos/upload/", fileUpload, config);
        };
        
        this.replaceEmptyDataWithNullOfCompany = function(company){
            if(company.phone == ''){
                company.phone = null;
            }
            
            if(company.fax == ''){
                company.fax = null;
            }
            
            if(company.address != null){
                if(company.address.postalCode == ''){
                   company.address.postalCode = null;
                }
            }
        }

        this.getPhotos = function(pageIndex,pageSize) {
            return $http.get("/api/photos?pageIndex=" + pageIndex + "&pageSize=" + pageSize);
        };
    });
