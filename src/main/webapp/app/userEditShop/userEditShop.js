'use strict';

angular.module('myApp.userEditShop', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/userEditShop', {
                    templateUrl: 'app/userEditShop/userEditShop.html',
                    controller: 'addShopCtrl'
                }),
                $routeProvider.when('/addFiles',{
                    templateUrl: 'app/userEditShop/addFiles.html'        
                })
                ;
            }]);

       
        

