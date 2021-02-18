package com.example.will.datalist.Models

import android.os.Parcel
import android.os.Parcelable

data class FoodGroup(val name: String, val image: String, val list: List<FoodItem>?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(FoodItem)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeTypedList(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodGroup> {
        override fun createFromParcel(parcel: Parcel): FoodGroup {
            return FoodGroup(parcel)
        }

        override fun newArray(size: Int): Array<FoodGroup?> {
            return arrayOfNulls(size)
        }
    }
}