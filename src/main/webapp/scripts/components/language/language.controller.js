'use strict';

angular.module('contactmgrApp')
    .controller('LanguageController', function($scope, $translate, Language) {
        $scope.changeLanguage = function(languageKey) {
            $translate.use(languageKey);
        };

        $scope.currentLanguage = Language.getCurrent;

        Language.getAll().then(function(languages) {
                    $scope.languages = languages;
                });
    });
