'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$authService', function($scope, $authService) {
	var vm = this;

	vm.currentUser = $authService.getCurrentUser();
	
	vm.isAuthenticated = function() {
//		vm.currentUser = $authService.currentUser;
		return $authService.isAuthenticated();
	};
	
//	$scope.$watch( $authService.currentUser, function ( currentUser ) {
//		console.log('isAuthenticated11 ', currentUser);
//		console.log('isAuthenticated22 ', $authService.currentUser);
//		console.log('isAuthenticated33 ', $authService.isAuthenticated());
//		console.log('getCurrentUser44 ', $authService.getCurrentUser());
//		vm.currentUser = $authService.getCurrentUser();
//	});
} ])

.controller('SignUpController', [ '$authService', function($authService) {
	var vm = this;
	vm.user = {};

	vm.signup = function() {
		$authService.signup(vm.user);
	};

} ])

.controller('LoginController', [ '$authService', function($authService) {
	var vm = this;
	vm.user = {};
	
	vm.login = function() {
		$authService.login(vm.user);
	};
	
	vm.authenticate = function(provider) {
		$authService.authenticate(provider);
	};

} ])

.controller('LogoutController', [ '$authService', function($authService) {
	$authService.logout();
} ]);
