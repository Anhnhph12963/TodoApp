package com.anhnh.todoapp.utils.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var taskId: Int,
    var task: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(taskId)
        parcel.writeString(task)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDoData> {
        override fun createFromParcel(parcel: Parcel): ToDoData {
            return ToDoData(parcel)
        }

        override fun newArray(size: Int): Array<ToDoData?> {
            return arrayOfNulls(size)
        }
    }
}

