'use strict';

angular.module('myApp.home')
.controller('ChangeNameCtrl', [ '$scope', '$auth', function($scope, $auth) {
	$scope.greetings = "Hello world";
	$scope.isAuthenticated = function() {
		return $auth.isAuthenticated();
	};
} ]);
