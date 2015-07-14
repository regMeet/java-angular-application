'use strict';

angular.module('myApp.profile')

.controller('ProfileCtrl', [ '$scope', '$alert', 'UserResource', function($scope, $alert, UserResource) {
		var vm = this;
    	vm.user = {};

		/**
		 * Get user's profile information.
		 */
//		$scope.getProfile = function() {
//			UserResource.getProfile()
//				.success(function(data) {
//					console.log(data);
//					$scope.user = data;
//				})
//				.error(function(error) {
//					$alert({
//						content : error.message,
//						animation : 'fadeZoomFadeDown',
//						type : 'material',
//						duration : 3
//					});
//				});
//		};
//
//		$scope.getProfile();
		
		vm.getProfile = function() {
			UserResource.getProfile()
			.success(function(data) {
				console.log(data);
				vm.user = data;
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

		vm.getProfile();
	} ]);
