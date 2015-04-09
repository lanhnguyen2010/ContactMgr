'use strict';

angular.module('contactmgrApp') //add object lib ngTable
    .config(function($stateProvider) {
        $stateProvider
            .state('contact', {
                parent: 'site',
                url: '/contact',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/contact/contact.html',
                        controller: 'ContactController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('contact');
                        return $translate.refresh();
                    }]
                }
            });
    });
