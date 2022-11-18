package com.example.tarea3_persistencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tarea3_persistencia.databinding.FragmentFirstBinding;
import com.example.tarea3_persistencia.db.AppDatabase;
import com.example.tarea3_persistencia.db.ProductViewModel;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RecyclerView rv;
    private ArrayList<AppDatabase.Product> products = new ArrayList<>();
    private recyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductViewModel mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

//        String uri = "gs://te-practica3.appspot.com/16177568_10211360956249913_3417240120267809241_o.jpg";
//        mProductViewModel.insert(new AppDatabase.Product("GTX3080","Nvidia", (float)99.99, uri));
        //products = (ArrayList<Product>) mProductViewModel.findAll().getValue();

        adapter = new recyclerAdapter();
        mProductViewModel.findAll().observe(getViewLifecycleOwner(), products ->{
            adapter.setList(products);
        });

        rv = binding.rv;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        //run this once


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}