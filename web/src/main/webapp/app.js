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
                redirectTo: '/'
            });
        // Parametros de configuración de satellizer
        //$authProvider.authHeader = 'Authorization'; // default
        $authProvider.authHeader = 'x-access-token';
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
        // https://github.com/sahat/satellizer/issues/261
        $authProvider.tokenRoot = 'entity'; // set the token parent element if the token is not the JSON root

        $authProvider.baseUrl = 'http://localhost:8089/web-services';
        $authProvider.loginUrl = '/auth/login';
        $authProvider.signupUrl = '/auth/signup';
        $authProvider.tokenName = 'token';
        $authProvider.tokenPrefix = 'myApp';
    }]);
