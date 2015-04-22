'use strict';

angular.module('contactmgrApp')
    .service('UsersService', function($http) {

        this.deleteUsers = function (ids) {
            return $http.delete("/api/users/?ids=" + ids, {});
        };
        this.activateUsers = function (ids) {
            return $http.put("/api/users/active/?ids=" + ids, {});
        };
        this.deactivateUsers = function (ids) {
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
        this.saveUser = function(user){
            return (user.id <= 0) ?  $http.post("/api/users", user) : $http.put("/api/users/" + user.id , user);      
        };
});
