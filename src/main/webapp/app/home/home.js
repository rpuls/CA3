'use strict';

angular.module('myApp.home', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/home', {
                    templateUrl: 'app/home/home.html',
                    controller: 'ShopCtrl',
                    controllerAs: 'ctrl'
                });
            }]);