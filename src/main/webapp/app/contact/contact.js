'use strict';

angular.module('myApp.contact', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/contact', {
                templateUrl: 'app/contact/contact.html',
  });
}]);