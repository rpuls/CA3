'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'angular-jwt',
  'ui.bootstrap',
  'myApp.security',
  'myApp.view1',
  'myApp.view2',
  'myApp.view3',
  'myApp.shopDetailsView',
  'myApp.view5',
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
  $routeProvider.otherwise({redirectTo: '/view1'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('AuthInterceptor');
});


