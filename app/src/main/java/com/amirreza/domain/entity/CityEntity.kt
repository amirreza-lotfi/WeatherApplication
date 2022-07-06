package com.amirreza.domain.entity

class CityEntity {
    var lat: Double
        private set
    var lon: Double
        private set
    var city: String? = null
        private set
    var country: String? = null
        private set

    constructor(lat: Double, lon: Double, city: String?, country: String?) {
        this.lat = lat
        this.lon = lon
        this.city = city
        this.country = country
    }

    constructor(lat: String, lon: String) {
        this.lat = lat.toDouble()
        this.lon = lon.toDouble()
    }
}