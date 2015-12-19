'use strict';

angular.module('myApp.home', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
    	// TODO: fix / home, now it's working as /#/
        $stateProvider
            .state('base', {
                url: '/',
                templateUrl: '/templates/home/home.html',
            })
            .state('home', {
                url: '/home',
                templateUrl: 'templates/home/home.html',
            });
    }]);
