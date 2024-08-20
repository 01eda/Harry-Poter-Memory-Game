package com.eda.myapplication

import android.os.Parcel
import android.os.Parcelable
import android.service.autofill.FieldClassification.Match
import android.service.carrier.CarrierIdentifier

data class kart (val identifier:Int,
                 var isFaceUp: Boolean=false,
                 var isMatched:Boolean=false )