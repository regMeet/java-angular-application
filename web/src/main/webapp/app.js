'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.view1',
    'userServices'
])
.config(['$routeProvider', '$httpProvider',
    function($routeProvider, $httpProvider) {
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
    }]);
