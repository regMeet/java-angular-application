'use strict';

angular.module('myApp.handling-error', [])

.controller('HandlingErrorCtrl', [ '$scope', '$authenticationService', '$state', 'AUTH_EVENTS', 'ERROR', 
                                   function($scope, $authenticationService, $state, AUTH_EVENTS, ERROR) {

	var vm = this;
	vm.alerts = [
//         { type: 'warning', msg: 'Warning' },
//         { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//         { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

	vm.closeAlert = function(index) {
		vm.alerts.splice(index, 1);
    };

	$scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
		console.log("not authorized event triggered");
		$authenticationService.logout();
		$state.go("unauthorized");
	});

	$scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
		console.log("not Authenticated event triggered");
		$authenticationService.logout();
		$state.go("login");
	});

	$scope.$on(ERROR.failedDependency, function(event, response) {
		vm.alerts.push({type: 'danger', msg: response.data.message});
	});

	$scope.$on(ERROR.badRequest, function(event, response) {
		//vm.alerts.push({type: 'danger', msg: response.data.message});
		//[{"field":"emailOrUsername","message":"forgot.emailOrUsername.length"}]
	});

} ]);
