package com.amirreza.domain.entity

class CityEntity(lat: String, lon: String) {
    var lat: Double
        private set
    var lon: Double
        private set
    var city: String? = null
        private set
    var country: String? = null
        private set

    init {
        this.lat = lat.toDouble()
        this.lon = lon.toDouble()
    }
}