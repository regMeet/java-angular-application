'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$authenticationService', '$rootScope', '$location', 'AUTH_EVENTS', function($scope, $authenticationService, $rootScope, $location, AUTH_EVENTS) {
	var vm = this;
	vm.currentUser = $authenticationService.currentUser;

	vm.isAuthenticated = function() {
		return $authenticationService.isAuthenticated();
	};
	
	$scope.$watch(function() {
		return $authenticationService.currentUser;
	},
	function(newVal) {
		vm.currentUser = newVal;
	});

//	$scope.$watch('$authenticationService.currentUser', function(newVal) {
//		console.log('hey, myVar has changed!', newVal);
//		vm.currentUser = newVal;
//	});

	$scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
		console.log("not authorized event triggered");
		// TODO: Remove credentials ?
		$location.path("/unauthorized").replace();
	});

	$scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
		console.log("not Authenticated event triggered");
		$location.path("/login").replace();
	});

} ])

.controller('SignUpController', [ '$authenticationService', function($authenticationService) {
	var vm = this;
	vm.user = {};

	vm.signup = function() {
		$authenticationService.signup(vm.user);
	};

} ])

.controller('LoginController', [ '$authenticationService', function($authenticationService) {
	var vm = this;
	vm.user = {};
	
	vm.login = function() {
		$authenticationService.login(vm.user);
	};
	
	vm.authenticate = function(provider) {
		$authenticationService.authenticate(provider);
	};

} ])

.controller('LogoutController', [ '$authenticationService', function($authenticationService) {
	$authenticationService.logout();
} ]);
