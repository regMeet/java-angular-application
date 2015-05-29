'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    "satellizer",
    'myApp.view1',
    'userServices',
    'myApp.oauth'
])
.config(['$routeProvider', '$httpProvider',
    function($routeProvider, $httpProvider, $authProvider) {
		$httpProvider.defaults.useXDomain = true;
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
        $routeProvider
            /*
            .when('/view1', {
                templateUrl: 'components/view1/view1.html',
                controller: 'View1Ctrl'
            })
            })*/
            .otherwise({
                redirectTo: '/view1'
            });
        // Parametros de configuración de satellizer
        $authProvider.loginUrl = "http://api.com/auth/login";
        $authProvider.signupUrl = "http://api.com/auth/signup";
        $authProvider.tokenName = "token";
        $authProvider.tokenPrefix = "myApp";
    }]);
