package com.example.cliqbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cliqbuzz.Fragments.Chat;
import com.example.cliqbuzz.Fragments.Contact;
import com.example.cliqbuzz.Fragments.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {

	BottomNavigationView bottomNavigation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		bottomNavigation = findViewById(R.id.bottom_navigation);
		bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle("CliqBuzz");
		setSupportActionBar(toolbar);


	}

	public void openFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.toolbar_options,menu);


		return  true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.menuAbout:
				Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menuSettings:
				Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menuLogout:
				Toast.makeText(this	,"Logout",Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}

	BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
			new BottomNavigationView.OnNavigationItemSelectedListener() {
				@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
					switch (item.getItemId()) {
						case R.id.navigation_status:
							openFragment(Status.newInstance("", ""));
							return true;
						case R.id.navigation_chat:
							openFragment(Chat.newInstance("", ""));
							return true;
						case R.id.navigation_contact:
							openFragment(Contact.newInstance("", ""));
							return true;
					}
					return false;
				}
			};
}
