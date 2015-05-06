'use strict';

angular.module('contactmgrApp')
    .controller('NavbarController', function($scope, $cookies, $rootScope, $location, $state,$http) {
        $scope.token = $cookies.token;
        $http.defaults.headers.get = { 'X-Auth-Token' : $scope.token };

        $scope.isLogin = false;
        function init(){
            $scope.getUsername();
        }
        $scope.username='';
        $scope.getUsername = function(){
            $http.get("api/security/current-user").success(function(user){
               $scope.username = user.username;
               $scope.isLogin = true;
            })
        }
        $scope.logout = function(){
            delete $cookies.token;
            console.log("logout");
            $state.reload();
        }
        init();
    });
