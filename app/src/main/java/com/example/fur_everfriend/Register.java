package com.example.fur_everfriend;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Register extends Fragment {
    private Button loginButton;
    public Register() {
        // Required empty public constructor
    }
    private int currentIndex = 0;
    private Handler handler = new Handler();
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

            // Define a runnable to cycle through the gradients
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    currentIndex++;
                    GradientDrawable nextGradient = gradients[currentIndex % gradients.length];
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] {view.getBackground(), nextGradient});
                    view.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(1750);
                    handler.postDelayed(this, 1750);
                }
            };

            // Start the runnable to cycle through the gradients
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
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        loginButton = view.findViewById(R.id.button5);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new instance of fragment to show on sign up button click
                Login loginFragment = new Login();

                // Replace current fragment with new fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}