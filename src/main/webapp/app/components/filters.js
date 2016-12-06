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
                if (!angular.isUndefined(phoneNumber)) {
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
                }
                return "Not available"
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
                if (!angular.isUndefined(link)) {
                    if (!link.match(/http/)) {
                        link = 'http://' + link;
                    }
                    return link;
                }
            };

        })

        .filter('linkTxtFilter', function () {

            return function (rawLink) {

                var link = rawLink;
                if (!angular.isUndefined(link)) {
                    link = link.replace(/https?:\/\//, '');
                    if (link.length > 17) {
                        link = "visit website"
                    }
                    return link;
                }
                return "";
            };

        })

        .filter('distMeter', function () {
            return function (decimal) {
                return Math.round(decimal / 25) * 25;
            };
        })

        .filter('catFilter', ['selectedCatFactory', function (selectedCatFactory) {
                return function (shopData) {
                    var category = selectedCatFactory.getSelectedCat();
                    console.log(category);

                    var filteredShops = shopData.filter(function (shopItem) {

                        if (angular.equals(shopItem.category, category)) {
                            return true;
                        } else if (angular.equals(category, 'uncategorised')) {
                            return shopData;
                        } else if (angular.equals(category, 'Show All')) {
                            return shopData;
                        }

                    });

                    return filteredShops;

                };
            }])

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
        })

        .filter('streetColorFilter', function () {
            return function (input) {
                var out;
                if (angular.isUndefined(input)) {
                    out = "aaaaaa";
                    return out;
                } else {
                    switch (input.toUpperCase()) {
                        case "STEFANSGADE" || "RANTZAUSGADE" || "GRIFFENFELDSGADE" || "ELMEGADE" || "BLEGDAMSVEJ" || "BRAGESGADE":
                            out = "ef3638";
                            break;
                        case "NØRREBROGADE":
                            out = "2b6a37";
                            break;
                        case "MIMERSGADE" || "GULDBERGSGADE" || "SANKT HANS GADE":
                            out = "ecbe1e";
                            break;
                        case "JAGTVEJ":
                            out = "703092";
                            break;
                        case "TAGENSVEJ":
                            out = "aeb0b0";
                            break;
                        case "ÅBOULEVARD":
                            out = "c3e5db";
                            break;
                        case "JÆGERSBORGGADE":
                            out = "795141";
                            break;
                        case "MØLLEGADE":
                            out = "09bbb5";
                            break;
                        case "BIRKEGADE" || "AHORNSGADE":
                            out = "f6902d";
                            break;
                        case "BLÅGÅRDSGADE" || "FÆLLEDVEJ" || "NØRRE ALLE":
                            out = "253991";
                            break;
                        case "RAVNSBORGGADE" || "RYESGADE":
                            out = "f0569b";
                            break;
                        case "WESSELSGADE" || "PEBLINGE DOSSERING" || "SORTEDAM DOSSERING":
                            out = "63be56";
                            break;
                        default:
                            out = "black";
                            break;
                    }
                }
                return out;
            };
        });

