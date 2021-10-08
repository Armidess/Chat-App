package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySigninBinding;
import com.example.whatsappclone.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog dialog;
    ActivitySigninBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        auth=FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.bntGooglesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


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
    int RC_SIGN_IN=65;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            User users = new User();
                            users.setDp(user.getPhotoUrl().toString());
                            users.setUserID(user.getUid());
                            users.setName(user.getDisplayName());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                            startActivity(new Intent(Signin.this,Homescreen.class));
                            Toast.makeText(Signin.this, "SignIn Using Google", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

}