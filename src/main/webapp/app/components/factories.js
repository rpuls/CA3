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
                getIsAdmin: function(){ return isAdmin; },
                setIsAdmin: function(input){ isAdmin = input; },
                getIsUser: function(){ return isUser; },
                setIsUser: function(input){ isUser = input; }
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
        .factory('$geolocation', ['$rootScope', '$window', '$q', function($rootScope, $window, $q) {

        function supported() {
            return 'geolocation' in $window.navigator;
        }

        var retVal = {
            getCurrentPosition: function(options) {
                var deferred = $q.defer();
                if(supported()) {
                    $window.navigator.geolocation.getCurrentPosition(
                        function(position) {
                            $rootScope.$apply(function() {
                                retVal.position.coords = position.coords;
                                retVal.position.timestamp = position.timestamp;
                                deferred.resolve(position);
                            });
                        },
                        function(error) {
                            $rootScope.$apply(function() {
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

            watchPosition: function(options) {
                if(supported()) {
                    if(!this.watchId) {
                        this.watchId = $window.navigator.geolocation.watchPosition(
                            function(position) {
                                $rootScope.$apply(function() {
                                    retVal.position.coords = position.coords;
                                    retVal.position.timestamp = position.timestamp;
                                    delete retVal.position.error;
                                    $rootScope.$broadcast('$geolocation.position.changed', position);
                                });
                            },
                            function(error) {
                                $rootScope.$apply(function() {
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

            clearWatch: function() {
                if(this.watchId) {
                    $window.navigator.geolocation.clearWatch(this.watchId);
                    delete this.watchId;
                }
            },

            position: {}
        };

        return retVal;
    }])
        ;



