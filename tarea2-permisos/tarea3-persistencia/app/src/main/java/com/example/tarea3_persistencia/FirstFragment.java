package com.example.tarea3_persistencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tarea3_persistencia.Models.Product;
import com.example.tarea3_persistencia.databinding.FragmentFirstBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RecyclerView rv;
    private ArrayList<Product> products;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv);

        products = new ArrayList<>();
        try {
            setProductInfo();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        setAdapter();
        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    private void setProductInfo() throws URISyntaxException {
        String uri = "https://www.funkykit.com/wp-content/uploads/2017/01/Titan-X.jpg";
        products.add(new Product("001","GTX3080",uri, (float)99.99,"Nvidia"));
    }

    private void setAdapter(){
        recyclerAdapter adapter = new recyclerAdapter(products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}