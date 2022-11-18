package com.example.tarea3_persistencia.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(AppDatabase.Product product);

    @Query("DELETE FROM product_table")
    void deleteAll();

    @Query("SELECT * from product_table ORDER BY uuid ASC")
    LiveData<List<AppDatabase.Product>> getAllProducts();

    @Query("SELECT * FROM product_table WHERE uuid = :uuid")
    LiveData<AppDatabase.Product> getEntity(String uuid);

    @Delete
    void delete (AppDatabase.Product product);

    @Update
    public void updateProduct(AppDatabase.Product product);

}
