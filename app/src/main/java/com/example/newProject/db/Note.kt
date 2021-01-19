package com.example.newProject.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    // @ColumnInfo(name = "note_title") : column들의 name을 별도로 지정이 가능
    val date: String,
    val title: String,
    val note: String
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}