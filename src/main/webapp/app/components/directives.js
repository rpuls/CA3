'use strict';

/* Place you Global Directives in this file */

angular.module('myApp.directives', [])
        .directive('mapDir', function ($window) {
            return {
                restrict: 'A',
                link: function (scope, element) {

                    var mapDiv = element;

                    angular.element(document).ready(function () {
                        mapDiv.height(mapDiv.width() * 1.45);
                    });
                    function onResize() {
                        mapDiv.height(mapDiv.width() * 1.45);
                    }
                    onResize();
                    angular.element($window).on('resize', onResize);
                }
            };
        })
        .directive('ngConfirmClick', [
            function () {
                return {
                    link: function (scope, element, attr) {
                        var msg = attr.ngConfirmClick || "Are you sure?";
                        var clickAction = attr.confirmedClick;
                        element.bind('click', function (event) {
                            if (window.confirm(msg)) {
                                scope.$eval(clickAction);
                                event.preventDefault();
//                            $location.path('/shopDetailsView');
                            }
//                        if(event && !confirmed){
//                        }
                        });
                    }
                };
            }])

        .directive('fileInput', function ($parse) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    var model = $parse(attrs.fileInput);
                    var modelSetter = model.assign;

                    element.bind('change', function () {
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files[0]);
                        });
                    });
                }
            };
        })
;

