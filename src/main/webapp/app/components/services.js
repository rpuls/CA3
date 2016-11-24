'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
        .service('InfoService', [function () {
                var info = "Hello World from a Service";
                this.getInfo = function () {
                    return info;
                };
            }]).
        service('NullCheckService', function (input) {
            if (angular.isUndefined(input)) {
                return 'N/A';
            } else {
                return input;
            }
        })
        .service('ShopService', function ($http) {
            var shop = {};
            shop.getShops = function () {
                return $http.get('api/shop/all'); //<--<-- rest API
            };
            shop.getUserShop = function (username) {
                var url = 'api/shop/usershop/'+username;
                return $http.get(url); //<--<-- rest API
            };
            return shop;

        });