'use strict';

angular.module('contactmgrApp')
    .controller('HomeController', function($scope, $state, $rootScope, $http, $cookies) {
        
        $scope.token = $rootScope.token;
        $scope.token = $cookies.token;
        if($scope.token !=null){
            $http.defaults.headers.get = { 'X-Auth-Token' : $scope.token };
            $http.defaults.headers.post = { 'X-Auth-Token' : $scope.token };
            $http.defaults.headers.put = { 'X-Auth-Token' : $scope.token };
            $http.defaults.headers.post['Content-Type'] = 'application/json';
        }
    
        function init(){
           $scope.getRole();
        }
  
        $scope.firstLogin = $rootScope.firstLogin;
        if($scope.firstLogin == true){
            $state.reload();
            $rootScope.firstLogin = false;
        }

        $scope.getRole = function(){
            $http.get("api/security/current-user").success(function(user){
                $scope.role = user.role;
                $scope.isLogin = true;
            })
        }
        
        $scope.isAdmin = function(){
            if ($scope.role === "ADMINISTRATOR"){
                return true;
            } else {
                return false;
            }
        }
        
        $scope.isDesigner = function(){
            if ($scope.role === "DESIGNER"){
                return true;
            } else {
                return false;
            }
        }
        
        $scope.isEditor = function(){
            if ($scope.role === "EDITOR"){
                return true;
            } else {
                return false;
            }
        }
        init();
    })
