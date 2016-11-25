'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'angular-jwt',
  'ui.bootstrap',
  'myApp.security',
  'myApp.home',
  'myApp.login',
  'myApp.addShop',
  'myApp.shopDetailsView',
  'myApp.shopsList',
  'myApp.userEditShop',
  'myApp.positionTool',
  'myApp.documentation',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services',
  'myApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('AuthInterceptor');
});


