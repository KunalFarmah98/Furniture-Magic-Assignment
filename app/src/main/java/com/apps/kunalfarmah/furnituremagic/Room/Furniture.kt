package com.apps.kunalfarmah.furnituremagic.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FURNITURE")
class Furniture {
    constructor() {}

    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String? = null
    var type: String? = null
    var price: String? = null
    var discountedPrice: String? = null
    var image: String? = null
    var user: String? = null

    constructor(
        name: String?,
        type: String?,
        price: String?,
        discountedPrice: String?,
        image: String?,
        user: String?
    ) {
        this.name = name
        this.type = type
        this.price = price
        this.discountedPrice = discountedPrice
        this.image = image
        this.user = user
    }

}