'use strict';

angular.module('myApp.view1')

.controller('View1Ctrl',
		[ '$scope', '$alert', 'User', function($scope, $alert, User) {
			// $scope.people = User.query();
			// $scope.people = User.getUsers();

			/**
			 * Get user's profile information.
			 */
			$scope.getUsers = function() {
				User.getUsers().success(function(data) {
					$scope.people = data;
				}).error(function(error) {
					$alert({
						content : error.message,
						animation : 'fadeZoomFadeDown',
						type : 'material',
						duration : 3
					});
				});
			};

			$scope.getUsers();
		} ]);
