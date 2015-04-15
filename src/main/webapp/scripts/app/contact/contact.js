'use strict';

angular.module('contactmgrApp')
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
            })
            .state('contact-edit', {
                parent: 'site',
                url: '/contact/edit',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/contact/contact-edit.html',
                        controller: 'EditContactController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('contact-edit');
                        return $translate.refresh();
                    }]
                }
            });
    });
