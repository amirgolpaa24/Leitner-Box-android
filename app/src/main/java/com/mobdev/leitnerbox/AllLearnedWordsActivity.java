package com.mobdev.leitnerbox;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllLearnedWordsActivity extends AppCompatActivity {

    private Button backButton;
    private LinearLayout linearLayout;

    private int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alllearnedwords);

        backButton = findViewById(R.id.allLearnedWordsactivity_back_button);
        linearLayout = findViewById(R.id.allLearnedWords_linearLayout);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        LeitnerCardList leitnerCardList = new LeitnerCardList(6, false);
        int size = leitnerCardList.getReadyCardNum();

        for (int i = 0; i < size; i++) {
            LeitnerCardList.LeitnerCard card = leitnerCardList.getCardAtPosition(i);
            String wordText = card.getWord();
            String meaningText = card.getMeaning();

            TextView wordTextView  = getWordTextView(
                    i + 1,
                    wordText,
                    R.color.colorPurpleLight,
                    R.font.raleway_semibold,
                    6
            );

            TextView meaningTextView = getMeaningTextView(
                    i + 1,
                    meaningText,
                    R.color.colorPurpleDark,
                    R.font.raleway_light,
                    5
            );

            linearLayout.addView(wordTextView);
            linearLayout.addView(meaningTextView);
        }

    }



    private TextView getWordTextView(int number, String wordText, int textColorID, int textFontID, int textSizeSP) {
        TextView wordTextView = new TextView(this);
        wordTextView.setText(number + "- " + wordText);
        wordTextView.setBackground(getDrawable(R.drawable.learned_word_background));
        wordTextView.setTypeface(getResources().getFont(textFontID));
        wordTextView.setTextColor(getResources().getColor(textColorID, null));
        wordTextView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSP, getResources().getDisplayMetrics()));

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginLeft = 10, marginTop = 7, marginRight = 10, marginBottom = 0;
        if(number == 1) marginTop = 14;
        marginLayoutParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginRight, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginBottom, getResources().getDisplayMetrics())
        );
        wordTextView.setLayoutParams(marginLayoutParams);

        wordTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        wordTextView.setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics())
        );

        return wordTextView;
    }

    private TextView getMeaningTextView(int number, String meaningText, int textColorID, int textFontID, int textSizeSP) {
        TextView meaningTextView = new TextView(this);
        meaningTextView.setText(meaningText);
        meaningTextView.setBackground(getDrawable(R.drawable.learned_meaning_background));
        meaningTextView.setTypeface(getResources().getFont(textFontID));
        meaningTextView.setTextColor(getResources().getColor(textColorID, null));
        meaningTextView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSP, getResources().getDisplayMetrics()));

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginLeft = 10, marginTop = 0, marginRight = 10, marginBottom = 7;
        if(number == size) marginBottom = 14;
        marginLayoutParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginRight, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginBottom, getResources().getDisplayMetrics())
        );
        meaningTextView.setLayoutParams(marginLayoutParams);

        meaningTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        meaningTextView.setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics())
        );
        meaningTextView.setTextDirection(View.TEXT_DIRECTION_RTL);

        return meaningTextView;
    }



}


















