package com.example.myapplication.contacts;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {
    private static ContactDB instance;

    public abstract ContactDao contactDao();

    public static synchronized ContactDB getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDB.class, "contact_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
