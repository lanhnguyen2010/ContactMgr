'use strict';

angular.module('contactmgrApp')
    .controller('LanguageController', function($scope, $translate, Language, $http) {
        
        function init(){
            $scope.initLanguage();
        }
        $scope.initLanguage = function(){
            $http.get('api/security/current-user').success(function(user){
                $scope.languageKey = user.language;
                $translate.use($scope.languageKey);
            })
        }
        $scope.changeLanguage = function(languageKey) {
            $http.put("/api/users/updateLanguage/" + languageKey);
            $translate.use(languageKey);
        };

        $scope.currentLanguage = Language.getCurrent;

        Language.getAll().then(function(languages) {
                    $scope.languages = languages;
                });
        init();
    });
