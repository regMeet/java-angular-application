'use strict';

angular.module('myApp.users', [ 'ngRoute', 'ngResource' ]).config(
		[ '$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

			$routeProvider.when('/user-list', {
				templateUrl : 'templates/users/user-list.html',
				controller : 'UserListCtrl',
				controllerAs: 'vm',
				access: {
		            authorizedRoles: [USER_ROLES.admin]
		        }
			});
			$routeProvider.when('/user-detail/:id', {
				templateUrl : 'templates/users/user-detail.html',
				controller : 'UserDetailCtrl',
				controllerAs: 'vm',
				access: {
		            authorizedRoles: [USER_ROLES.admin]
		        }
			});
			$routeProvider.when('/user-creation', {
				templateUrl : 'templates/users/user-creation.html',
				controller : 'UserCreationCtrl',
				controllerAs: 'vm',
				access: {
		            authorizedRoles: [USER_ROLES.admin]
		        }
			});
		} ]);
