'use strict';

angular.module('contactmgrApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('greetings', {
                parent: 'site',
                url: '/greetings',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/greeting/greetings.html',
                        controller: 'GreetingController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('greetings');
                        return $translate.refresh();
                    }]
                }
            })
            .state('new', {
                parent: 'greetings',
                url: '/new',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/greeting/edit-greeting.html',
                        controller: 'GreetingController'
                    }
                }
            });
    });
