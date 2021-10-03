package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog dialog;
    ActivitySigninBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        auth=FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        dialog= new ProgressDialog(Signin.this);
        dialog.setTitle("Logging In");
        dialog.setMessage("Logging In");
        getSupportActionBar().hide();
        binding.bntSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.siemail.length()>0 && binding.sipassword.length()>6)
                {
                    dialog.show();
                    auth.signInWithEmailAndPassword(binding.siemail.getText().toString(),binding.sipassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull  Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                dialog.dismiss();
                                Toast.makeText(Signin.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signin.this,Homescreen.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Signin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(Signin.this,Homescreen.class));
            finish();
        }
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,Signup.class));
                finish();
            }
        });
    }
}