'use strict';

angular.module('contactmgrApp')
    .factory('Language', function($q, $http, $translate, LANGUAGES) {
        return {
            getCurrent: function() {
                var language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');

                if (angular.isUndefined(language)) {
                    language = 'en';
                }

                return language;
            },
            getAll: function() {
                var deferred = $q.defer();
                deferred.resolve(LANGUAGES);
                return deferred.promise;
            }
        };
    })
    .constant('LANGUAGES', ['en', 'vi']);




