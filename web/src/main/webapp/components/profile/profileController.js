'use strict';

angular.module('myApp.profile')

.controller('ProfileCtrl', [ '$scope', '$alert', 'UserResource', function($scope, $alert, UserResource) {
		/**
		 * Get user's profile information.
		 */
		$scope.getUsers = function() {
			UserResource.getUsers().success(function(data) {
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
