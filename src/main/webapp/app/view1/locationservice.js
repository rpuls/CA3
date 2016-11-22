var positions = [{name: "Nørreport_Station", gps_pos: new google.maps.LatLng(55.6831194, 12.5715059), css_pos: {top: "96.5%", left: "45%"}}];
positions.push({name: "Nørrebro_Station", gps_pos: new google.maps.LatLng(55.7008504, 12.5356172), css_pos: {top: "3%", left: "45%"}});

function initGeolocation() {
    if (navigator && navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
    } else {
        console.log('Geolocation is not supported');
    }
}

function errorCallback() {}

function successCallback(position) {

    var _yourCord = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

    min = google.maps.geometry.spherical.computeDistanceBetween(positions[0].gps_pos, _yourCord);
    positionsIndex = 0;
    for (var i = 1; i < positions.length; i++) {
        dist = google.maps.geometry.spherical.computeDistanceBetween(positions[i].gps_pos, _yourCord);
        if (min > dist) {
            min = dist;
            positionsIndex = i;
        }
    }

    console.log(positions[positionsIndex].name);

    $("#mapPos").css("left", positions[positionsIndex].css_pos.left);
    $("#mapPos").css("top", positions[positionsIndex].css_pos.top);

}