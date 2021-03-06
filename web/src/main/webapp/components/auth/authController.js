'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$authenticationService', '$rootScope', '$state',
                          function($scope, $authenticationService, $rootScope, $state) {
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
    var vm = this;

    vm.logout = function() {
        $authenticationService.logoutBackend()
        .success(function(user) {
            $authenticationService.logout();
        })
        .error(function(error) {

        });
    };

    vm.logout();

} ])

.controller('VerifyController', [ '$authenticationService', '$stateParams', function($authenticationService, $stateParams) {
	var vm = this;
	vm.status = '';

	vm.verify = function() {
		$authenticationService.verify($stateParams.token)
		.success(function(status) {
			vm.status = "successfully";
        })
        .error(function(error) {
			vm.status = "There was an error";
        });
	};

	vm.verify();

} ])

.controller('ForgotPasswordController', [ '$authenticationService', function($authenticationService) {
	var vm = this;
	vm.emailOrUsername = '';

	vm.forgotPassword = function() {
		$authenticationService.forgotPassword(vm.emailOrUsername);
	};

} ])

.controller('PasswordForgottenController', [ '$authenticationService', '$stateParams', '$state', function($authenticationService, $stateParams, $state) {
	var vm = this;
	vm.user = {};
	vm.tokenValidated = false;
	vm.error = '';

	vm.passwordForgotten = function() {
		$authenticationService.passwordForgotten($stateParams.token, vm.user.password)
		.success(function(status) {
			vm.tokenValidated = true;
			$state.go("login");
        })
        .error(function(error) {
			vm.error = error.message;
        });
	};

} ]);
