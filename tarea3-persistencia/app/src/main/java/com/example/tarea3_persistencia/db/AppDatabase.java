package com.example.tarea3_persistencia.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AppDatabase.Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static String NAME = "pucmm";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME)
                        .build();
            }
        }
        return INSTANCE;
    }


    public abstract ProductDao productDao();

    @Entity(tableName = "product_table")
    public static class Product {
        @PrimaryKey
        @NonNull
        private String uuid;
        private String name;
        private String imageUrl;
        private String brand;
        private float price;

        public Product() {
            uuid = null;
        }

        public Product(String name, String brand, float price, String imageUrl) {
            this.uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();;
            this.name = name;
            this.imageUrl = imageUrl;
            this.price = price;
            this.brand = brand;
        }


        public String getUuid() {
            return uuid;
        }
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }

        public String getBrand() {
            return brand;
        }
        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
