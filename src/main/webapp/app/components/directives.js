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
                            }
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
                        if (this.files.length > 4) {
                            element[0].files = null;
                            alert('TOO MANY FILES. Please select 4 images only.');
                            scope = "";
                            element[0].files = "";
                        }
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files);
                        });
                    });
                }
            };
        })

        .directive('openingHours', function () {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    shop: '='
                },
                template: function () {
                    var date = new Date();
                    var weekDay = date.getDay();
                    switch(weekDay){
                        case 0 : return '<div>{{shop.dayNullOpen | hourFilter}} - {{shop.dayNullClose | hourFilter}}</div>';
                        case 1 : return '<div>{{shop.dayOneOpen | hourFilter}} - {{shop.dayOneClose | hourFilter}}</div>';
                        case 2 : return '<div>{{shop.dayTwoOpen | hourFilter}} - {{shop.dayTwoClose | hourFilter}}</div>';
                        case 3 : return '<div>{{shop.dayThreeOpen | hourFilter}} - {{shop.dayThreeClose | hourFilter}}</div>';
                        case 4 : return '<div>{{shop.dayFourOpen | hourFilter}} - {{shop.dayFourClose | hourFilter}}</div>';
                        case 5 : return '<div>{{shop.dayFiveOpen | hourFilter}} - {{shop.dayFiveClose | hourFilter}}</div>';
                        case 6 : return '<div>{{shop.daySixOpen | hourFilter}} - {{shop.daySixClose | hourFilter}}</div>';
                    }
                }
            };
        });
;

