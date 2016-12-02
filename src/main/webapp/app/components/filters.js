'use strict';

/* Place your Global Filters in this file */

angular.module('myApp.filters', [])
        .filter('checkmark', function () {
            return function (input) {
                return input ? '\u2713' : '\u2718';
            };
        })


        .filter('phoneFilter', function () {

            return function (rawPhoneNumber) {

                var phoneNumber = rawPhoneNumber;
                phoneNumber = phoneNumber.replace(/ /g, '');
                if (phoneNumber[0] != '+') {
                    phoneNumber = '+45' + phoneNumber;
                }
                phoneNumber = phoneNumber.substr(0, 3) + ' '
                        + phoneNumber.substr(3, 2) + ' '
                        + phoneNumber.substr(5, 2) + ' '
                        + phoneNumber.substr(7, 2) + ' '
                        + phoneNumber.substring(9, phoneNumber.length);
                return phoneNumber;
            };

        })
        .filter('hourFilter', function () {
            return function (rawHours) {
                var formattedHours = rawHours.toString();
                if (formattedHours == "0") {
                    return "";
                }
                if (formattedHours.length < 4) {
                    formattedHours = "0" + formattedHours
                }
                formattedHours = formattedHours.substr(0, 2)
                        + ':'
                        + formattedHours.substring(2, formattedHours.length);
                return formattedHours;
            };
        })

        .filter('hrefFilter', function () {

            return function (rawLink) {

                var link = rawLink;
                if (!link.match(/http/)) {
                    link = 'http://' + link;
                }
                return link;
            };

        })

        .filter('linkTxtFilter', function () {

            return function (rawLink) {

                var link = rawLink;
                link = link.replace(/https?:\/\//, '');
                if (link.length > 17) {
                    link = "visit website"
                }
                return link;
            };

        })

        .filter('distMeter', function () {
            return function (decimal) {
                return Math.round(decimal / 25) * 25;
            };
        })

        .filter('catFilter', function (selectedCatFactory) {

            return function (shopData, category) {
                console.log('this is the shop object: ' + shopData);


//                var filteredShops = [];
                category = selectedCatFactory.getSelectedCat();
//                angular.forEach(shopData, function (value, key) {
//                    if (angular.equals(value.category, category)) {
//                        filteredShops.push(value, key);
//
//                        console.log('name of added shop :' + value.name + ' and id : ' + key);
//                        console.log('size of filtered list :' + filteredShops.length);
//                    }
//                    console.log(filteredShops);
//
//                    return filteredShops;
//
//                });
//                 return shopData.category === category;
                 return shopData;


            };
        })

        .filter('categoryfilter', function () {
            return function (input) {
                var out;
                switch (input) {
                    case "CUCA":
                        out = "CUP & CAKE";
                        break;
                    case "FOOD":
                        out = "FOODIES";
                        break;
                    case "TAWA":
                        out = "TAKE AWAY";
                        break;
                    case "ETHN":
                        out = "ETHNIC";
                        break;
                    case "DRIN":
                        out = "DRINKS";
                        break;
                    case "BEER":
                        out = "BEER BAR BODEGA";
                        break;
                    case "SEDL":
                        out = "SEE + DO + LISTEN";
                        break;
                    case "MUSI":
                        out = "MUSIC";
                        break;
                    case "CURI":
                        out = "CURIOSITIES";
                        break;
                    case "PAPE":
                        out = "PARKS & PEACE";
                        break;
                    case "BEBS":
                        out = "BEAUTY BODY & SOUL";
                        break;
                    case "LESC":
                        out = "LEARN & SCHOOL";
                        break;
                    case "HINS":
                        out = "HELP - I NEED SOMEBODY";
                        break;
                    case "HOHY":
                        out = "HOME & HYGGE";
                        break;
                    case "CONV":
                        out = "CONVENIENCE";
                        break;
                    case "HAND":
                        out = "HANDMADE";
                        break;
                    case "SHFA":
                        out = "SHOP & FASHION";
                        break;
                    case "WINE":
                        out = "WINE";
                        break;
                    case "VINT":
                        out = "VINTAGE";
                        break;
                    case "VINY":
                        out = "VINYL";
                        break;
                    default:
                        out = "Uncategorised";
                        break;
                }
                return out;
            };
        });

