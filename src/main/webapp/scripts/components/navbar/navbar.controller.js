'use strict';

angular.module('contactmgrApp')
    .controller('NavbarController', function($scope, $cookieStore, $rootScope, $location, $state,$http) {
        $scope.$state = $state;

        $scope.isLogin = false;
        function init(){
            $scope.getUsername();
        }
        $scope.username='';
        $scope.getUsername = function(){
            $http.get("api/security/current-user").success(function(user){
               $scope.username = user.username;
               $scope.isLogin = true;
            });
        }
        $scope.logout = function(){
            $cookieStore.remove("JSESSIONID");
            console.log("logout");
            $location.path("/login");
        }
        init();
    });
