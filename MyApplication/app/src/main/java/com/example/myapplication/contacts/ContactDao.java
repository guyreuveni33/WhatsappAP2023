package com.example.myapplication.contacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(int id);

    @Query("DELETE FROM contact")
    public void nukeTable();

    @Insert
    void insert(Contact... contact);
    @Update
    void update(Contact... contact);

    @Delete
    void delete(Contact... contact);
}