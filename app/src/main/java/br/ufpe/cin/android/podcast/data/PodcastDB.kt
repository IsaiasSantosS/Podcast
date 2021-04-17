package br.ufpe.cin.android.podcast.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Classe que represa o banco de dados do sitema
@Database(entities = [Episodio::class], version = 1)
abstract class PodcastDB: RoomDatabase() {
    abstract fun dao() : EpisodioDAO

    //Padrão singleton
    companion object{
        @Volatile
        private var INSTANCE : PodcastDB? = null
        fun getInstance(c:Context) : PodcastDB {
            return INSTANCE ?: synchronized(this){
                val instance: PodcastDB = Room.databaseBuilder(
                    c.applicationContext,
                    PodcastDB::class.java,
                    "podcast.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}