package com.example.fur_ever_friend;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dash_BoardFragment extends Fragment {

    CircleImageView user_img;
    ImageView camera_icon,edit_name;
    RadioGroup genderGroup;
    Button update;
    Uri mImageUri;
    DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;
    EditText user_description,userName,phone_number;
    AlertDialog materialAlertDialogBuilder;
    private static final int PICK_IMAGE_REQUEST = 1;

    public Dash_BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash__board, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        user_img = view.findViewById(R.id.user_img);
        camera_icon = view.findViewById(R.id.camera_icon);
        edit_name=view.findViewById(R.id.edit_name);

        genderGroup = view.findViewById(R.id.gender_group);
        update = view.findViewById(R.id.update_btn);
        user_description = view.findViewById(R.id.user_desc);
        userName=view.findViewById(R.id.userName);
        userName.setEnabled(false);
        phone_number=view.findViewById(R.id.Phone_Number);
        phone_number.setEnabled(false);
        sharedPreferences= getContext().getSharedPreferences("login", MODE_PRIVATE);
        DatabaseReference walkerRef = mDatabase.child("users");
        String mobile = sharedPreferences.getString("mobile", "");
        phone_number.setText(mobile);

        walkerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isAdded()) {
                    if (snapshot.child(mobile).child("imageUrl").exists()) {
                        String imageUrl = snapshot.child(mobile).child("imageUrl").getValue().toString();
                        Glide.with(getContext()).load(imageUrl).into(user_img);
                    }
                }
                if (snapshot.child(mobile).child("Fullname").exists()){
                    String name=snapshot.child(mobile).child("Fullname").getValue().toString();
                    userName.setText(name);
                }
                if (snapshot.child(mobile).child("description").exists()){
                    String description=snapshot.child(mobile).child("description").getValue().toString().trim();
                    user_description.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setEnabled(true);
                userName.setFocusable(true);
                userName.requestFocus();
                userName.setSelection(userName.getText().length());

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                imm.showSoftInput(userName, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        userName.requestFocus();
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = view.findViewById(i);
                if (radioButton != null) {
                    String selectedOption = radioButton.getText().toString();
                    Toast.makeText(view.getContext(), selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context dialogContext = new ContextThemeWrapper(view.getContext(), R.style.AlertDialogTheme);
                MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(dialogContext);
                        builder.setTitle("Update")
                        .setMessage("Do you want to update the data")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "Positive Button clicked", Toast.LENGTH_SHORT).show();
                                uploadName();
                                uploadDesc();
                                uploadImage();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "Negative Button clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        return view;
    }

    public void uploadName() {
        if (userName != null) {
            String mobile = sharedPreferences.getString("mobile", "");
            DatabaseReference walkerRef = mDatabase.child("users").child(mobile);
            String updatedName = userName.getText().toString().trim();
            walkerRef.child("Fullname").setValue(updatedName)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            userName.setText(updatedName);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure to update the name
                        }
                    });
        }
    }

    public void uploadDesc() {
        if (user_description!=null){
            String mobile = sharedPreferences.getString("mobile", "");
            DatabaseReference walkerRef = mDatabase.child("users").child(mobile);
            String updatedDescription = user_description.getText().toString().trim();
            walkerRef.child("description").setValue(updatedDescription)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            user_description.setText(updatedDescription);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure to update the name
                        }
                    });

        }
    }

    private void uploadImage() {
        if (mImageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + mImageUri.getLastPathSegment());
            UploadTask uploadTask = storageRef.putFile(mImageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            String imageUrl = uri.toString();

                            String mobile = sharedPreferences.getString("mobile", "");

                            DatabaseReference walkerRef = mDatabase.child("users").child(mobile); // Update the child node path as per your database structure
                            walkerRef.child("imageUrl").setValue(imageUrl);

                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                            Glide.with(getContext()).load(imageUrl).into(user_img);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading " + (int) progress + "%");
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            user_img.setImageURI(mImageUri);
        }
    }

}
