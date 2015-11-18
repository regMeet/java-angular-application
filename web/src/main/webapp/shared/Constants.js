'use strict';

angular.module('myApp.auth')

.constant('AUTH_EVENTS', {
  notAuthenticated: 'auth-not-authenticated',
  notAuthorized: 'auth-not-authorized'
})

.constant('USER_ROLES', {
  ADMIN: 'ADMIN',
  USER: 'USER',
  public: 'public_role'
});
