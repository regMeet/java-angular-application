'use strict';

angular.module('myApp.profile')

.controller('ProfileCtrl', [ '$scope', '$alert', 'UserResource', function($scope, $alert, UserResource) {
		/**
		 * Get user's profile information.
		 */
		$scope.getProfile = function() {
			UserResource.getProfile()
				.success(function(data) {
					console.log(data);
					$scope.user = data.entity;
				})
				.error(function(error) {
					$alert({
						content : error.message,
						animation : 'fadeZoomFadeDown',
						type : 'material',
						duration : 3
					});
				});
		};

		$scope.getProfile();
	} ]);
