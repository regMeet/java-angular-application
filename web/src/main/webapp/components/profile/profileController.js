'use strict';

angular.module('myApp.profile')

.controller('ProfileCtrl', [ '$scope', '$alert', 'UserResource', '$authenticationService', function($scope, $alert, UserResource, $authenticationService) {
		var vm = this;
    	vm.user = {};

		/**
		 * Get user's profile information.
		 */
		vm.getProfile = function() {
			UserResource.getProfile()
			.success(function(user) {
				vm.user = user;
				$authenticationService.setCurrentUser(user.username);
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
