'use strict';

angular.module('contactmgrApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('contact-edit', {
                parent: 'site',
                url: '/contact-edit',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/contact-edit/contact-edit.html'
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
