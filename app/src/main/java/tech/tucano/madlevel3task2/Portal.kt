package tech.tucano.madlevel3task2

import android.os.Parcelable
import java.net.URL
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Portal (
    var title: String,
    var url: String
): Parcelable