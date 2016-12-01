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
        .factory('userFactory', function () {
            var username = "";
            return {
                getUser: function () {
                    return username;
                },
                setUser: function (user) {
                    username = user;
                    return username;
                }

            };
        })

        .factory('userAdminFactory', function () {
            var isAdmin = false;
            var isUser = false;
            return {
                getIsAdmin: function () {
                    return isAdmin;
                },
                setIsAdmin: function (input) {
                    isAdmin = input;
                },
                getIsUser: function () {
                    return isUser;
                },
                setIsUser: function (input) {
                    isUser = input;
                }
            };
        })
        .factory('selectedCatFactory', function () {

            console.log('factory initialised');
            var selectedCat = {};

            return {
                getSelectedCat: function () {
                    return selectedCat;
                },
                setSelectedCat: function (input) {

                    selectedCat = input;
                    return selectedCat;
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
            positions.push({name: "Nørrebro St.", gps_pos: new google.maps.LatLng(55.7008504, 12.5356172), css_pos: {top: "3", left: "45"}});

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
