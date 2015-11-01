'use strict';

angular.module('myApp.auth')

.run([ '$rootScope', '$location', '$authenticationService', '$authorizationService', 'AUTH_EVENTS', function($rootScope, $location, $authenticationService, $authorizationService, AUTH_EVENTS) {
	var routeChangeRequiredAfterLogin = false,
    loginRedirectUrl;

	$rootScope.$on('stateChangeStart', function (event, next) {
	    var authorised;
//	    console.log(' ');
//	    console.log('in ', next.originalPath);
	    
	    if (routeChangeRequiredAfterLogin && next.originalPath!=="/login"){
//	    	console.log("routeChangeRequiredAfterLogin ");
	    	routeChangeRequiredAfterLogin = false;
            $location.path(loginRedirectUrl).replace();
	    }
	    
	    if ('access' in next && 'authorizedRoles' in next.access) {
//	    	console.log('need access ');
	    	if (!$authenticationService.isAuthenticated()) {
		    	if (next.name !== '/login') {
//		    		console.log('copying url ');
	    			routeChangeRequiredAfterLogin = true;
	    			loginRedirectUrl = next.originalPath;
					$rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
	    		}
		    } else {
		    	var authorizedRoles = next.access.authorizedRoles;
		        if (!$authorizationService.isAuthorized(authorizedRoles)) {
//		        	console.log("no esta authorizado ");
		        	$rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
		        }
		    }
	    }
	});
} ]);
