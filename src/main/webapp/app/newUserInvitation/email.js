'use strict';

angular.module('myApp.newUserInvitation', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/sendEmail', {
                    templateUrl: 'app/newUserInvitation/sendEmail.html',
                    controller: 'addShopCtrl'
                });
            }]);
