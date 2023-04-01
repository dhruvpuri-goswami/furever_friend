package com.example.fur_everfriend;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login extends Fragment {

    private int currentIndex = 0;
    private Handler handler = new Handler();
    private ImageButton google_login;
    private Button signUpButton;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        if (view != null) {
            // Create new gradient drawables using the XML files
            GradientDrawable gradient1 = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_one);
            GradientDrawable gradient2 = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_two);
            GradientDrawable gradient3 = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_three);

            // Create an array of the gradient drawables
            final GradientDrawable[] gradients = {gradient1, gradient2, gradient3};

            // Set initial background
            view.setBackground(gradients[currentIndex % gradients.length]);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    currentIndex++;
                    GradientDrawable nextGradient = gradients[currentIndex % gradients.length];
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{view.getBackground(), nextGradient});
                    view.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(1750);
                    handler.postDelayed(this, 1750);
                }
            };
            handler.post(runnable);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUpButton = view.findViewById(R.id.button3);
        google_login = view.findViewById(R.id.google);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requireContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new instance of fragment to show on sign up button click
                Register signUpFragment = new Register();

                // Replace current fragment with new fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, signUpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // ...
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // ...
                        }
                    }
                });
    }
}

