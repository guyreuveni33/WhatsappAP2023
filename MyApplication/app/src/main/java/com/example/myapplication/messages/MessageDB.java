package com.example.myapplication.messages;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.entities.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageDB extends RoomDatabase {

    private static MessageDB instance;

    public abstract MessageDao messageDao();

    public static synchronized MessageDB getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MessageDB.class, "message_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public static synchronized MessageDB getDatabase() {
        return instance;
    }

}