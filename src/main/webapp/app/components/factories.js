'use strict';
/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
        factory('InfoFactory', function () {
            var info = "Hello World from a Factory";
            var getInfo = function getInfo() {
                return info;
            };
            return {
                getInfo: getInfo
            };
        })

        .factory('loginFactory', function ($window, jwtHelper) {
            return {
                setLoginInfoOnScope: function ($scope) {
                    $scope.username = "";
                    $scope.isAuthenticated = false;
                    $scope.isAdmin = false;
                    $scope.isUser = false;
                    var token = $window.sessionStorage.id_token;
                    if (token) {
                        var tokenPayload = jwtHelper.decodeToken(token);
                        $scope.username = tokenPayload.username;
                        $scope.isAuthenticated = true;
                        tokenPayload.roles.forEach(function (r) {
                            if (r === "Admin") {
                                $scope.isAdmin = true;
                            } else if (r === "User") {
                                $scope.isUser = true;
                            }
                        });
                    }
                }
            };
        })
        .factory('selectedCatFactory', function () {

            var selectedCat = 'Show All';
            return {
                getSelectedCat: function () {
                    return selectedCat;
                },
                setSelectedCat: function (input) {
                    selectedCat = input;
                }
            };
        })

        .factory('selectedShopFac', function () {
            var selectedShop = {};
            return {
                getSelectedShop: function () {
                    return selectedShop;
                },
                setSelectedShop: function (shop) {
                    selectedShop = shop;
                    return selectedShop;
                }

            };
        })
        .factory('filesFactory', function () {
            var files = {};
            return {
                getFiles: function () {
                    return files;
                },
                setFiles: function (shopFiles) {
                    files = shopFiles;
                    return files;
                }
            };
        })

        .factory('geolocationFactory', function ($rootScope, $window, $q, mapPositionFactory) {

            function supported() {
                return 'geolocation' in $window.navigator;
            }

            var retVal = {
                getCurrentPosition: function (options) {
                    var deferred = $q.defer();
                    if (supported()) {
                        $window.navigator.geolocation.getCurrentPosition(
                                function (position) {
                                    $rootScope.$apply(function () {
                                        retVal.position.coords = position.coords;
                                        retVal.mapPosition = mapPositionFactory.calculateMapPos(position);
                                        retVal.position.timestamp = position.timestamp;
                                        deferred.resolve(position);
                                    });
                                },
                                function (error) {
                                    $rootScope.$apply(function () {
                                        deferred.reject({error: error});
                                    });
                                }, options);
                    } else {
                        deferred.reject({error: {
                                code: 2,
                                message: 'This web browser does not support HTML5 Geolocation'
                            }});
                    }
                    return deferred.promise;
                },
                watchPosition: function (options) {
                    if (supported()) {
                        if (!this.watchId) {
                            this.watchId = $window.navigator.geolocation.watchPosition(
                                    function (position) {
                                        $rootScope.$apply(function () {
                                            retVal.position.coords = position.coords;
                                            retVal.position.timestamp = position.timestamp;
                                            delete retVal.position.error;
                                            $rootScope.$broadcast('$geolocation.position.changed', position);
                                        });
                                    },
                                    function (error) {
                                        $rootScope.$apply(function () {
                                            retVal.position.error = error;
                                            delete retVal.position.coords;
                                            delete retVal.position.timestamp;
                                            $rootScope.$broadcast('$geolocation.position.error', error);
                                        });
                                    }, options);
                        }
                    } else {
                        retVal.position = {
                            error: {
                                code: 2,
                                message: 'This web browser does not support HTML5 Geolocation'
                            }
                        };
                    }
                },
                clearWatch: function () {
                    if (this.watchId) {
                        $window.navigator.geolocation.clearWatch(this.watchId);
                        delete this.watchId;
                    }
                },
                position: {}
            };
            return retVal;
        })
        .factory('mapPositionFactory', function () {
            var positions = [{name: "Nørreport St.", gps_pos: new google.maps.LatLng(55.6831194, 12.5715059), css_pos: {top: "96.5", left: "45"}}];
            positions.push({name: "Nørrebro St.", gps_pos: new google.maps.LatLng(55.7008504, 12.5356172), css_pos: {top: "3.1", left: "38.3"}});
            positions.push({name: "Lygten", gps_pos: new google.maps.LatLng(55.701644, 12.537584), css_pos: {top: "11", left: "53"}});
            positions.push({name: "Nørrebro parken", gps_pos: new google.maps.LatLng(55.693760, 12.541583), css_pos: {top: "31,7", left: "19.3"}});
            positions.push({name: "assistentens kirkegård", gps_pos: new google.maps.LatLng(55.690994, 12.550051), css_pos: {top: "49", left: "38.3"}});
            positions.push({name: "Rundelen", gps_pos: new google.maps.LatLng(55.694344, 12.548774), css_pos: {top: "31.7", left: "40"}});
            positions.push({name: "Skt. Hans Torv", gps_pos: new google.maps.LatLng(55.690756, 12.560677), css_pos: {top: "61.4", left: "71.8"}});
            positions.push({name: "Blågårds Plads", gps_pos: new google.maps.LatLng(55.686393, 12.557151), css_pos: {top: "69", left: "18.4"}});
            positions.push({name: "Dr. Louises Bro", gps_pos: new google.maps.LatLng(55.686677, 12.563972), css_pos: {top: "90", left: "40"}});
            var func = {
                calculateMapPos: function (realPosition) {
                    var yourCord = realPosition.coords;
                    var yourCordGoogle = new google.maps.LatLng(yourCord.latitude, yourCord.longitude);
                    var min = google.maps.geometry.spherical.computeDistanceBetween(positions[0].gps_pos, yourCordGoogle);
                    var positionsIndex = 0;
                    for (var i = 1; i < positions.length; i++) {
                        var dist = google.maps.geometry.spherical.computeDistanceBetween(positions[i].gps_pos, yourCordGoogle);
                        if (min > dist) {
                            min = dist;
                            positionsIndex = i;
                        }
                    }
                    var result = positions[positionsIndex];
                    result.dist = min;
                    return result;
                }
            };
            return func;
        });
