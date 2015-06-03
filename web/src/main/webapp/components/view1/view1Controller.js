'use strict';

angular.module('myApp.view1')

.controller('View1Ctrl', [ '$scope', 'User', function($scope, User) {
		$scope.people = User.query();
} ]);
