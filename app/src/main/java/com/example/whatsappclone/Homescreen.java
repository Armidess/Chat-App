package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Adapters.FragmentAdapter;
import com.example.whatsappclone.databinding.ActivityHomescreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Homescreen extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityHomescreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        auth= FirebaseAuth.getInstance();
        binding = ActivityHomescreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.setting:
                Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(Homescreen.this,Signin.class));
                finish();
                break;
        }
        return true;
    }
}