<<<<<<< HEAD
=======
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
    });
>>>>>>> f16a9b4c65466765549031ca657954298986461f
