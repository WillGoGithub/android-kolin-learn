package com.example.will.goactivity.Models

import android.os.Parcel
import android.os.Parcelable

data class Schedule(
    var arctic: Boolean,
    var antarctic: Boolean,
    var finland: Boolean): Parcelable {

    var answerList = mapOf(
        "北極" to arctic,
        "南極" to antarctic,
        "芬蘭" to finland)

    var hasAnyChecked: Boolean = false
        get () {
            return answerList.any { it.value }
        }

    fun getCheckedValues(): Array<String> {
        return answerList.filter { it.value }.keys.toTypedArray()
    }

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (arctic) 1 else 0)
        parcel.writeByte(if (antarctic) 1 else 0)
        parcel.writeByte(if (finland) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Schedule> {
        override fun createFromParcel(parcel: Parcel): Schedule {
            return Schedule(parcel)
        }

        override fun newArray(size: Int): Array<Schedule?> {
            return arrayOfNulls(size)
        }
    }
}