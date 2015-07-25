'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$authService', '$rootScope', function($scope, $authService, $rootScope) {
	var vm = this;
	vm.currentUser = $authService.currentUser;

	vm.isAuthenticated = function() {
		return $authService.isAuthenticated();
	};
	
	$scope.$watch(function() {
		return $authService.currentUser;
	},
	function(newVal) {
		vm.currentUser = newVal;
	});

//	$scope.$watch('$authService.currentUser', function(newVal) {
//		console.log('hey, myVar has changed!', newVal);
//		vm.currentUser = newVal;
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
