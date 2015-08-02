angular.module('myApp.directives')

.directive('access', ['$authorizationService', 'removeElement', function ($authorizationService, removeElement) {
    return{
        restrict: 'A',
        link: function (scope, element, attributes) {
        	
            var authorizedRoles = attributes.access.split(",");
            if (!$authorizationService.isAuthorized(authorizedRoles)) {
                angular.forEach(element.children(), function (child) {
                    removeElement(child);
                });
                removeElement(element);
            }
        }
    }
}])

.constant('removeElement', function(element){
    element && element.remove && element.remove();
});