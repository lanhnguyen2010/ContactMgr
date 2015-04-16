'use strict';

angular.module('contactmgrApp')
    .service('UsersService', function($http) {
        this.deleteUsers = function (ids) {
            return $http.delete("/api/users/?ids=" + ids, {});
        };
        this.activateUsers = function (ids) {
            return $http.put("/api/users/setactive/?ids=" + ids, {});
        };
        this.inactivateUsers = function (ids) {
            return $http.put("/api/users/setdeactive/?ids=" + ids, {});
        };
        this.searchUsers = function (filter, page, pageSize) {
            return $http.post("/api/users/search?page=" + page + "&pageSize=" + pageSize, filter);
        };
        this.getCompanies=function(){
            return $http.get("/api/companies/names");
        };
        this.getRoles=function(){
            return $http.get("/api/users/roles");
        };
        this.saveUser = function(user){
               console.log("userid:"+ user.id);
                return (user.id <= 0) ?  $http.post("/api/users", user) : $http.put("/api/users/?id=" + user.id , user);
                
        };
    })