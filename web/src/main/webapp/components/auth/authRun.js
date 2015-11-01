'use strict';

angular.module('myApp.auth')

.run([ '$rootScope', '$state', '$authenticationService', '$authorizationService', 'AUTH_EVENTS', function($rootScope, $state, $authenticationService, $authorizationService, AUTH_EVENTS) {
	var routeChangeRequiredAfterLogin = false,
    loginRedirectStateName;

	$rootScope.$on('$stateChangeStart', function (event, next) {
	    var authorised;
	    // console.log(' ');
	    // console.log('in ', next.name);

	    if (routeChangeRequiredAfterLogin && next.name!=="login" && $authenticationService.isAuthenticated()){
	    	// console.log("routeChangeRequiredAfterLogin ");
	    	routeChangeRequiredAfterLogin = false;
            //$location.path(loginRedirectUrl).replace();
            // console.log('next redirect: ', loginRedirectStateName);
            event.preventDefault();
			$state.go(loginRedirectStateName); // replace
	    }
	    
	    if ('access' in next && 'authorizedRoles' in next.access) {
	    	// console.log('need access ');
	    	if (!$authenticationService.isAuthenticated()) {
		    	if (next.name !== '/login') {
		    		// console.log('copying url ');
	    			routeChangeRequiredAfterLogin = true;
                    loginRedirectStateName = next.name;
                    event.preventDefault();
                    // $state.go("login");
					$rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
	    		}
		    } else {
		    	var authorizedRoles = next.access.authorizedRoles;
		        if (!$authorizationService.isAuthorized(authorizedRoles)) {
		        	// console.log("not authorized");
                    event.preventDefault();
                    // $state.go("unauthorized");
		        	$rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
		        }
		    }
	    }
	});
} ]);
