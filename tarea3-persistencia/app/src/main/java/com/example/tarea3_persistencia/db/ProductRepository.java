package com.example.tarea3_persistencia.db;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<AppDatabase.Product>> mAllproducts;


    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        mAllproducts = productDao.getAllProducts();
    }

    public void insert(AppDatabase.Product product) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.insert(product));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.deleteAll());
    }

    public LiveData<List<AppDatabase.Product>> findAll() {
        return mAllproducts;
    }

    public void delete(AppDatabase.Product product){
        AppDatabase.databaseWriteExecutor.execute(()->
                productDao.delete(product));
    }

    public LiveData<AppDatabase.Product> getEntity(String uuid){
        return productDao.getEntity(uuid);
    }

    public void update(AppDatabase.Product product){
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.updateProduct(product));
    }


}
