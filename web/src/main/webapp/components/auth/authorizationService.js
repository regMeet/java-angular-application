'use strict';

angular.module('myApp.auth')

.factory('$authorizationService', ['$authenticationService', function ($authenticationService) {

		// interface
	    var service = {
	    	isAuthorized: isAuthorized,
	    };
	    return service;

	    // implementation
	    // TODO: agregar multiples valores, login required, not authorized
	    function isAuthorized(authorizedRoles) {
    		if (!angular.isArray(authorizedRoles)) {
    			authorizedRoles = [authorizedRoles];
    		}

    		return authorizedRoles.indexOf($authenticationService.role) !== -1;
	    }

}]);
