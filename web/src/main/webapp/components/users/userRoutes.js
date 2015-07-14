'use strict';

angular.module('myApp.users', [ 'ngRoute', 'ngResource' ]).config(
		[ '$routeProvider', function($routeProvider) {
			// $routeProvider.when('/user-list', {
			// templateUrl: 'templates/users/user-list.html',
			// controller: 'UserListCtrl'
			// });

			$routeProvider.when('/user-list', {
				templateUrl : 'templates/users/user-list.html',
				controller : 'UserListCtrl',
				controllerAs: 'vm'
			});
			$routeProvider.when('/user-detail/:id', {
				templateUrl : 'templates/users/user-detail.html',
				controller : 'UserDetailCtrl',
				controllerAs: 'vm'
			});
			$routeProvider.when('/user-creation', {
				templateUrl : 'templates/users/user-creation.html',
				controller : 'UserCreationCtrl',
				controllerAs: 'vm'
			});
			// $routeProvider.otherwise({redirectTo: '/user-list'});
		} ]);
