package com.anhnh.todoapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")

data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int, val noteTitle: String, val noteDesc: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(), parcel.readString().toString(), parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(noteTitle)
        parcel.writeString(noteDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
