package com.example.tarea3_persistencia.db;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;

    private LiveData<List<AppDatabase.Product>> mAllProducts;

    public ProductViewModel(@NonNull @NotNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.findAll();
    }

    public void insert(AppDatabase.Product product) {
        mRepository.insert(product);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void delete(AppDatabase.Product product){mRepository.delete(product);}
    public LiveData<List<AppDatabase.Product>> findAll() {
        return mAllProducts;
    }
    public LiveData<AppDatabase.Product> getEntity(String uuid){return mRepository.getEntity(uuid);}

    public void update(AppDatabase.Product product){mRepository.update(product);}
}
