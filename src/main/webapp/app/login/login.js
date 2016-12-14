'use strict';

angular.module('myApp.login', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/login', {
                    templateUrl: 'app/login/login.html',
                    controller: 'LogInCtrl'
                });
            }])

        .controller('LogInCtrl', function ($window, $rootScope, $location, $http, $scope, $timeout, userAdminFactory, jwtHelper) {

                $scope.login = function () {
                    $http.post('api/login', $scope.user)
                            .success(function (data) {
                                $window.sessionStorage.id_token = data.token;
                                $rootScope.$broadcast("loginEvent", {token: data.token, status: true});
                                
                                var tokenPayload = jwtHelper.decodeToken(data.token);
                                tokenPayload.roles.forEach(function (role) {
                                if (role === "Admin") {
                                    userAdminFactory.setIsAdmin(true);
                                    console.log(role);
                                }
                                if (role === "User") {
                                    userAdminFactory.setIsUser(true);
                                    console.log(role);
                                }
                                });
                                $timeout(function () {

                                    $location.path("#/home");
                                }, 100);
                            })
                            .error(function (data) {
                                delete $window.sessionStorage.id_token;
                                $rootScope.$broadcast("loginEvent", {token: null, status: false});
//                      clearUserDetails($scope);
                            });
                };
            });