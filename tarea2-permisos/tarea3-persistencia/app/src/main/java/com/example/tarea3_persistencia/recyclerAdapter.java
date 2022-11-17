package com.example.tarea3_persistencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tarea3_persistencia.Models.Product;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Product> products;

    public recyclerAdapter(ArrayList<Product> products){
        this.products = products;
    }
    private NavController navController;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView nameText;
        private TextView brandText;
        private TextView priceText;
        private Button editBtn;
        private Button buyBtn;

        public MyViewHolder(final View view){
            super(view);
            img = view.findViewById(R.id.imageView2);
            nameText = view.findViewById(R.id.nameTextView);
            brandText = view.findViewById(R.id.brandTextView);
            priceText = view.findViewById(R.id.priceTextView);
            buyBtn = view.findViewById(R.id.addButton);
            editBtn = view.findViewById(R.id.editButton);
        }
    }

    @NonNull
    @NotNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull recyclerAdapter.MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameText.setText(product.getName());
        holder.brandText.setText(product.getBrand());
        holder.priceText.setText(String.valueOf(product.getPrice()));
        Picasso.get().load(product.getImageUrl()).into(holder.img);

        holder.buyBtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Add to Cart clicked", Toast.LENGTH_SHORT).show();
        });

        holder.editBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId", product.getUuid());
            Navigation.findNavController(v).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });

        holder.editBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
