package com.mobdev.leitnerbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BoxActivity extends AppCompatActivity implements DeletingWordDialog.DeletingWordDialogListener {

    private int boxNumber;
    private WordFragmentCollectionAdapter adapter;

    TextView textView;
    ViewPager viewPager;
    Button backButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras == null)
            return;

        boxNumber = 0;
        if (extras.containsKey("box_number"))
            boxNumber = extras.getInt("box_number");

        textView = findViewById(R.id.boxTitle_textView);
        String boxNumberString;
        switch (boxNumber) {
            case 1:
                boxNumberString = "First";
                break;
            case 2:
                boxNumberString = "Second";
                break;
            case 3:
                boxNumberString = "Third";
                break;
            case 4:
                boxNumberString = "Fourth";
                break;
            case 5:
                boxNumberString = "Fifth";
                break;
            default:
                boxNumberString = "Nth";
        }

        textView.setText("The " + boxNumberString + " Box");


        ////////////////////////////////////////////////////////////////////////////////

        viewPager = findViewById(R.id.words_viewPager);
        adapter = new WordFragmentCollectionAdapter(getSupportFragmentManager(), boxNumber);
        viewPager.setAdapter(adapter);

        backButton = findViewById(R.id.boxactivity_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    @Override
    public void applyDeleting(WordFragment wordFragment) {
        wordFragment.applyDeleting();
    }



}












