package com.example.cliqbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

	Button btnsave;
	private FirebaseAuth firebaseAuth;
	private TextView textViewemailname;
	private DatabaseReference databaseReference;
	private EditText editTextName;
	private EditText editTextSurname;
	private EditText editTextPhoneNo;
	private ImageView profileImageView;
	private static int PICK_IMAGE = 123;
	Uri imagePath;


	public UserProfile() {
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
			imagePath = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
				profileImageView.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		firebaseAuth=FirebaseAuth.getInstance();
		if (firebaseAuth.getCurrentUser() == null){
			finish();
			startActivity(new Intent(getApplicationContext(),LoginActivity.class));
		}

		databaseReference = FirebaseDatabase.getInstance().getReference();
		editTextName = (EditText)findViewById(R.id.EditTextName);
		editTextSurname = (EditText)findViewById(R.id.EditTextSurname);
		editTextPhoneNo = (EditText)findViewById(R.id.EditTextPhoneNo);
		btnsave=(Button)findViewById(R.id.btnSaveButton);
		FirebaseUser user=firebaseAuth.getCurrentUser();
		btnsave.setOnClickListener(this);
		textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
		textViewemailname.setText(user.getEmail());
		profileImageView = findViewById(R.id.update_imageView);
	}

	@Override
	public void onClick(View v) {

	}
}
