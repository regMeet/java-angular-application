'use strict';

angular.module('myApp.home', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('base', {
                url: '/',
                templateUrl: '/templates/home/home.html'
                // controller: 'HomeCtrl'
            })
            .state('home', {
                url: '/home',
                templateUrl: 'templates/home/home.html'
                // controller: 'HomeCtrl'
            });
    }]);
