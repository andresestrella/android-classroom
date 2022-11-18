package com.example.tarea3_persistencia;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.*;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.tarea3_persistencia.databinding.FragmentSecondBinding;
import com.example.tarea3_persistencia.db.AppDatabase;
import com.example.tarea3_persistencia.db.ProductViewModel;
import com.google.android.gms.tasks.*;
import com.google.firebase.storage.*;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private FragmentSecondBinding binding;
    private ImageView imgView;
    private EditText nameEdit;
    private EditText brandEdit;
    private EditText priceEdit;
    private Button deleteBtn;
    private ProgressBar progressBar;
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask mUploadTask;
    private String result;
    private AppDatabase.Product product;
    private String prodId;
    private ProductViewModel mProductViewModel;
    private boolean imgChanged = false;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new
                    ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    Picasso.get().load(uri).into(imgView);
                    imageUri = uri;
                    imgChanged = true;
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgView = view.findViewById(R.id.imageView);
        nameEdit = view.findViewById(R.id.nameEditText);
        brandEdit = view.findViewById(R.id.brandEditText);
        priceEdit = view.findViewById(R.id.priceEditText);
        Button saveBtn = view.findViewById(R.id.saveButton);
        Button clearBtn = view.findViewById(R.id.clearButton);
        deleteBtn = view.findViewById(R.id.deleteButton);
        progressBar = view.findViewById(R.id.progress_bar);
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        prodId = getArguments().getString("productId");
        if (prodId != null && !prodId.equals("0")){
            deleteBtn.setVisibility(View.VISIBLE);
            //find product in the database with the id
            product = mProductViewModel.getEntity(prodId).getValue();

            mProductViewModel.getEntity(prodId).observe(getViewLifecycleOwner(), prod ->{
                product = prod;
                //set the views with the info.
                nameEdit.setText(product.getName());
                brandEdit.setText(product.getBrand());
                priceEdit.setText(String.valueOf(product.getPrice()));
                imageUri = Uri.parse(product.getImageUrl());
                imgChanged = false;
                Picasso.get().load(product.getImageUrl()).into(imgView);
            });


        }

        imgView.setOnClickListener(v -> {
            //openFileChooser();
            mGetContent.launch("image/*");
        });

        saveBtn.setOnClickListener(v -> {
            //check que no hay parametros vacios
            String price = priceEdit.getText().toString().trim();
            if(nameEdit.getText().toString().length() == 0 || brandEdit.getText().toString().length() == 0 || price.length() == 0){
                Toast.makeText(getContext(), "There are empty fields", Toast.LENGTH_SHORT).show();
            }else{
                //upload image and get url
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEdit.setText("");
                brandEdit.setText("");
                priceEdit.setText("");
                //imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_24));
                imgView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_image_24));
                deleteBtn.setVisibility(View.INVISIBLE);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mProductViewModel.delete(mProductViewModel.getEntity(prodId).getValue());
                mProductViewModel.delete(product);
                Navigation.findNavController(v).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile() {

        if(imageUri != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            if (!imgChanged){
                save();
            }else{
                mUploadTask = fileReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //delays 3 seconds until setting the progress bar to 0.
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(0);
                                    }
                                }, 500);

                                Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                result = null;
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                progressBar.setProgress((int)progress);
                            }
                        });

                Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            result = downloadUri.toString();
                            save();
                        } else {
                            // Handle failures
                            Toast.makeText(getContext(), "No se pudo guardar imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }else{
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void save(){
        if (prodId != null && !prodId.equals("0")){
            product.setName(nameEdit.getText().toString());
            product.setBrand(brandEdit.getText().toString());
            product.setPrice(Float.parseFloat(priceEdit.getText().toString()));
            if (imgChanged) {
                product.setImageUrl(result);
            }
            mProductViewModel.update(product);
            Toast.makeText(getContext(), "Product Updated", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_SecondFragment_to_FirstFragment);
        }else{
            //create Product object
            AppDatabase.Product prod = new AppDatabase.Product(nameEdit.getText().toString(), brandEdit.getText().toString(), Float.parseFloat(priceEdit.getText().toString()), result);
            //save to database
            mProductViewModel.insert(prod);
            Toast.makeText(getContext(), "Product Saved", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_SecondFragment_to_FirstFragment);
        }
    }
    private void openFileChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imgView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}