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
import androidx.navigation.Navigation;
import com.example.tarea3_persistencia.Models.Product;
import com.example.tarea3_persistencia.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private FragmentSecondBinding binding;
    private ImageView imgView;
    private EditText nameEdit;
    private EditText brandEdit;
    private EditText priceEdit;
    private Button saveBtn;
    private Button clearBtn;
    private Button deleteBtn;
    private ProgressBar progressBar;
    private Uri imageUri;
    private StorageReference storageReference;
    private String result;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new
                    ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    Picasso.get().load(uri).into(imgView);
                    imageUri = uri;
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
        saveBtn = view.findViewById(R.id.saveButton);
        clearBtn = view.findViewById(R.id.clearButton);
        deleteBtn = view.findViewById(R.id.deleteButton);
        progressBar = view.findViewById(R.id.progress_bar);

        String prodId = getArguments().getString("productId");
        if (prodId != null){
            deleteBtn.setVisibility(View.VISIBLE);
            //find product in the database with the id
            Product product;
            //set the views with the info.
            /*nameEdit.setText(product.getName());
            brandEdit.setText(product.getBrand());
            priceEdit.setText(String.valueOf(product.getPrice()));
            Picasso.get().load(product.getImageUrl()).into(imgView);*/
        }

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openFileChooser();
                mGetContent.launch("image/*");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check que no hay parametros vacios
                if(nameEdit.getText().toString().length() == 0 ||
                brandEdit.getText().toString().length() == 0 ||
                priceEdit.getText().toString().length() == 0){
                    String newId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                    //upload iamge and get url
                    uploadFile();
                    //create Product object
                    if (result != null && !result.equals("")){
                        Product prod = new Product(newId, nameEdit.getText().toString(), brandEdit.getText().toString(), Float.parseFloat(priceEdit.getText().toString()), result);
                        //save to database

                    }


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
                //delete from database with prodId
                //prodId
                //send to fragment 1
                Navigation.findNavController(v).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });


        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile() {

        if(imageUri != null){
            StorageReference fileRefence = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileRefence.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //delays 3 seconds until setting the progress bar to 0.
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 3000);

                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                            result = taskSnapshot.getStorage().getDownloadUrl().toString();
                            //taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

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
        }else{
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
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