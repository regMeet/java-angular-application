'use strict';

angular.module('myApp.profile', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/profile', {
				templateUrl : 'templates/profile/profile.html',
				controller : 'ProfileCtrl',
				controllerAs : 'vm'
			});
		} ]);
