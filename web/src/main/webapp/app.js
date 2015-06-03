'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'satellizer',
    'myApp.auth',
    'myApp.home',
    'myApp.view1',
    'userServices'
])
.config(['$routeProvider', '$authProvider',
    function($routeProvider, $authProvider) {
        $routeProvider
            .otherwise({
                redirectTo: '/login'
            });
        // Parametros de configuración de satellizer
        //$authProvider.authHeader = 'Authorization'; // default
        $authProvider.authHeader = 'x-access-token';
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request

        $authProvider.baseUrl = 'http://localhost:8089/web-services';
        $authProvider.loginUrl = '/auth/login';
        $authProvider.signupUrl = '/auth/signup';
        $authProvider.tokenName = 'satellizer';
        $authProvider.tokenPrefix = 'myApp';
    }]);
