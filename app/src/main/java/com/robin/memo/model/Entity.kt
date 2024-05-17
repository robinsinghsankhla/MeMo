package com.robin.memo.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robin.memo.DataBase.DatabaseName


@Entity(tableName = DatabaseName.TABLENAME)
class ModelTable : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    @ColumnInfo("title")
    var title:String = ""
    @ColumnInfo("subTitle")
    var subTitle:String = ""
    @ColumnInfo("note")
    var note:String = ""
    @ColumnInfo("date")
    var date:String = ""
    @ColumnInfo("priority")
    var priority:String = ""
    constructor(id: Int?, title:String, subTitle:String, note:String, date:String, priority:String){
        this.id = id
        this.title = title
        this.subTitle = subTitle
        this.note = note
        this.date = date
        this.priority = priority
    }


    constructor(parcel: Parcel){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Entity> {
        override fun createFromParcel(parcel: Parcel): Entity? {
            return Entity(parcel.toString())
        }

        override fun newArray(size: Int): Array<Entity?> {
            return arrayOfNulls(size)
        }
    }
}