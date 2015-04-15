'use strict';

angular.module('contactmgrApp')
    .factory('Greeting', function ($resource) {
        return $resource('api/greetings/:id', {}, {
            'query': { method: 'GET', isArray: true },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
