package com.example.gapsiproyect.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gapsiproyect.daos.Results

@Entity(tableName = "Movies_Store") //room ; to create sqlite objects //one table
data class MovieResults (
    @Embedded
    public var Results: Results? = null
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMovie")
    public var idMovie // for data objects
            = 0
}
