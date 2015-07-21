'use strict';

angular.module('myApp.auth')

.factory('$authService', ['$auth', '$location', '$alert', function ($auth, $location, $alert) {
		var urlBase = 'http://localhost:8089/web-services/api/users/';
		var isLoggedIn = {};
		var currentUser;
		
		// interface
	    var service = {
    		currentUser : currentUser,
	        isAuthenticated: isAuthenticated,
	        getCurrentUser: getCurrentUser,
	        login: login,
	        signup: signup,
	        authenticate: authenticate,
	        logout: logout
	    };
	    return service;

	    // implementation
	    function isAuthenticated() {
	    	return $auth.isAuthenticated();
	    }
	    
	    function getCurrentUser() {
	    	console.log('getCurrentUser1 ' ,service.currentUser );
	    	console.log('getCurrentUser2 ' ,currentUser );
	    	
	    	return service.currentUser;
	    }
	    
	    function signup(user) {
	        $auth.signup({
	        	username: user.username,
	            email: user.email,
	            password: user.password
	        })
	        .then(function(response) {
	        	service.currentUser = response.data.currentUser;

	            // Si se ha registrado correctamente,
	            // Podemos redirigirle a otra parte
	        	console.log("signup successful");
	            $location.path("/user-list");
	        })
	        .catch(function(response) {
	            // Si ha habido errores, llegaremos a esta función
	        	console.log("errores en el signup");
	        });
	    }
	    
	    function login(user) {
	    	$auth.login({
		        email: user.email,
		        password: user.password
		    })
		    .then(function(response){
		    	service.currentUser = response.data.currentUser;

		        // Si se ha logueado correctamente, lo tratamos aquí.
		        // Podemos también redirigirle a una ruta
		    	$location.path("/user-list");
		    })
		    .catch(function(response){
		        // Si ha habido errores llegamos a esta parte
		    	console.log(response.data.message);
		    });
	    }
	    
	    function authenticate(provider) {
	    	$auth.authenticate(provider)
	        .then(function(response) {
	        	service.currentUser = response.data.currentUser;
		    	console.log('currentUser ', currentUser );
	        	currentUser = response.data.currentUser;
	        	console.log("service.currentUser ", service.currentUser);

		    	$location.path("/user-list");
	        })
	        .catch(function(response) {
	        	console.log(response.data.message);
	        });
	    }
	    
	    function logout() {
	    	if (!$auth.isAuthenticated()) {
	            return;
	        }
	        $auth.logout()
    		.then(function() {
    			// Desconectamos al usuario y lo redirijimos
                $location.path('/login');

    	        $alert({
    	          content: 'You have been logged out.',
    	          animation: 'fadeZoomFadeDown',
    	          type: 'material',
    	          duration: 3
    	        });
    		});
	    }
}]);
