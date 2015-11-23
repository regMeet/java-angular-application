'use strict';

angular.module('myApp.auth')

.factory('$authenticationService', ['$auth', '$state', '$alert', '$cookies', 'USER_ROLES', '$http', function ($auth, $state, $alert, $cookies, USER_ROLES, $http) {
		var logoutURL = 'http://localhost:8089/web-services/auth/logout';

		var userId = $cookies.get('userId');
		var currentUser = $cookies.get('currentUser');
		var role = $cookies.get('role');

		// interface
	    var service = {
    		userId: userId,
    		currentUser : currentUser,
    		setCurrentUser: setCurrentUser,
    		role: role,
	        isAuthenticated: isAuthenticated,
	        login: login,
	        signup: signup,
	        authenticate: authenticate,
	        logout: logout,
	        logoutBackend: logoutBackend
	    };
	    return service;

	    // implementation
	    function isAuthenticated() {
	    	return $auth.isAuthenticated();
	    }

	    function signup(user) {
	        $auth.signup({
	        	username: user.username,
	            email: user.email,
	            password: user.password
	        })
	        .then(function(response) {
	        	updateUser(response.data.user);

	            // Si se ha registrado correctamente,
	            // Podemos redirigirle a otra parte
	        	console.log("signup successful");
				$state.go("home");
	        })
	        .catch(function(response) {
	            // Si ha habido errores, llegaremos a esta función
	        	console.log("errores en el signup", response);
	        });
	    }

	    function login(user) {
	    	$auth.login({
	    		emailOrUsername: user.emailOrUsername,
		        password: user.password
		    })
		    .then(function(response){
		    	updateUser(response.data.user);

		        // Si se ha logueado correctamente, lo tratamos aquí.
		        // Podemos también redirigirle a una ruta
				$state.go("home");
		    })
		    .catch(function(response){
		        // Si ha habido errores llegamos a esta parte
		    	console.log("errores en el login", response);
		    });
	    }

	    function authenticate(provider) {
	    	$auth.authenticate(provider)
	        .then(function(response) {
	        	updateUser(response.data.user);

				$state.go("home");
	        })
	        .catch(function(response) {
	        	console.log(response.data.message);
	        });
	    }

	    function updateUser(user){
	    	service.userId = user.userId;
	    	service.currentUser = user.currentUser;

			if (user.role == 'ADMIN') {
				service.role = USER_ROLES.ADMIN;
			}
			if (user.role == 'USER') {
				service.role = USER_ROLES.USER;
			}

	    	$cookies.put('userId', service.userId);
	    	$cookies.put('currentUser', service.currentUser);
	    	$cookies.put('role', service.role);
	    }

	    function setCurrentUser(currentUser){
	    	service.currentUser = currentUser;
	    	$cookies.put('currentUser', service.currentUser);
	    }

	    function logout() {
	    	if (!$auth.isAuthenticated()) {
	            return;
	        }

	        $auth.logout()
    		.then(function() {
    			// Desconectamos al usuario y lo redirijimos
				$state.go("login");

    	        $alert({
    	          content: 'You have been logged out.',
    	          animation: 'fadeZoomFadeDown',
    	          type: 'material',
    	          duration: 3
    	        });
    		});
	    }

       function logoutBackend() {
            if (!$auth.isAuthenticated()) {
                return;
            }

            return $http.post(logoutURL);
        }
}]);
