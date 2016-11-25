'use strict';

angular.module('myApp.addShop', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/addShop', {
                    templateUrl: 'app/addShop/addShop.html',
                    controller: 'addShopCtrl'
                });
            }]);