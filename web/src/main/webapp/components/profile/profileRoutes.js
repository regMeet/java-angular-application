'use strict';

angular.module('myApp.profile', [ 'ngRoute' ]).config(
		[ '$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {
			$routeProvider.when('/profile', {
				templateUrl : 'templates/profile/profile.html',
				controller : 'ProfileCtrl',
				controllerAs : 'vm',
				access: {
					authorizedRoles: [USER_ROLES.user, USER_ROLES.admin]
		        }
			});
		} ]);
