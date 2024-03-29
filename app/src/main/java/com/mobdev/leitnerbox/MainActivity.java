package com.mobdev.leitnerbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddingWordDialog.AddingWordDialogListener {

    private Button firstBoxButton, secondBoxButton, thirdBoxButton, fourthBoxButton, fifthBoxButton;
    private TextView firstReadinessTextView, secondReadinessTextView, thirdReadinessTextView, fourthReadinessTextView, fifthReadinessTextView;
    private TextView firstDaysTextView, secondDaysTextView, thirdDaysTextView, fourthDaysTextView, fifthDaysTextView;
    private Button learnedWordsButton;
    private TextView learnedNumTextView;
    private Button addWordsButton, helpButton, settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstBoxButton = findViewById(R.id.firstBox_button);
        secondBoxButton = findViewById(R.id.secondBox_button);
        thirdBoxButton = findViewById(R.id.thirdBox_button);
        fourthBoxButton = findViewById(R.id.fourthBox_button);
        fifthBoxButton = findViewById(R.id.fifthBox_button);
        learnedWordsButton = findViewById(R.id.allLearnedWords_button);

        addWordsButton = findViewById(R.id.addWords_button);
        helpButton = findViewById(R.id.help_button);
        settingsButton = findViewById(R.id.settings_button);


        firstBoxButton.setOnClickListener(this);
        secondBoxButton.setOnClickListener(this);
        thirdBoxButton.setOnClickListener(this);
        fourthBoxButton.setOnClickListener(this);
        fifthBoxButton.setOnClickListener(this);
        learnedWordsButton.setOnClickListener(this);

        addWordsButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);




        firstReadinessTextView = findViewById(R.id.firstBoxReadiness_textView);
        secondReadinessTextView = findViewById(R.id.secondBoxReadiness_textView);
        thirdReadinessTextView = findViewById(R.id.thirdBoxReadiness_textView);
        fourthReadinessTextView = findViewById(R.id.fourthBoxReadiness_textView);
        fifthReadinessTextView = findViewById(R.id.fifthBoxReadiness_textView);
        learnedNumTextView = findViewById(R.id.learnedBoxReadiness_textView);

        LeitnerCardList leitnerCardList;
        int[] readyWords = new int[5];
        int[] allWords = new int[5];
        for (int i = 0; i < 5; i++) {
            leitnerCardList = new LeitnerCardList(i + 1);
            readyWords[i] = leitnerCardList.getReadyCardNum();
            allWords[i] = leitnerCardList.getCount();
        }
        leitnerCardList = new LeitnerCardList(6);
        int allLearnedWordsNum = leitnerCardList.getCount();

        firstReadinessTextView.setText("ready: " + readyWords[0] + " / " + allWords[0]);
        secondReadinessTextView.setText("ready: " + readyWords[1] + " / " + allWords[1]);
        thirdReadinessTextView.setText("ready: " + readyWords[2] + " / " + allWords[2]);
        fourthReadinessTextView.setText("ready: " + readyWords[3] + " / " + allWords[3]);
        fifthReadinessTextView.setText("ready: " + readyWords[4] + " / " + allWords[4]);
        learnedNumTextView.setText("" + allLearnedWordsNum);



        firstDaysTextView = findViewById(R.id.firstBoxDays_textView);
        secondDaysTextView = findViewById(R.id.secondBoxDays_textView);
        thirdDaysTextView = findViewById(R.id.thirdBoxDays_textView);
        fourthDaysTextView = findViewById(R.id.fourthBoxDays_textView);
        fifthDaysTextView = findViewById(R.id.fifthBoxDays_textView);

        int[] days = new int[5];
        int[] hours = new int[5];
        for (int i = 0; i < 5; i++) {
            int readyNum = new LeitnerCardList(i + 1, true).getReadyCardNum();

            leitnerCardList = new LeitnerCardList(i + 1, false);

            if(readyNum == 0) {
                long milliseconds = leitnerCardList.getMinMillisecondsTillReady();

                days[i] = LeitnerCardList.getDaysFromMilliseconds(milliseconds);
                hours[i] = LeitnerCardList.getHoursFromMilliseconds(milliseconds);
            } else {
                days[i] = 0;
                hours[i] = 0;
            }
        }

        firstDaysTextView.setText(days[0] + " days : " + hours[0] + " hours");
        secondDaysTextView.setText(days[1] + " days : " + hours[1] + " hours");
        thirdDaysTextView.setText(days[2] + " days : " + hours[2] + " hours");
        fourthDaysTextView.setText(days[3] + " days : " + hours[3] + " hours");
        fifthDaysTextView.setText(days[4] + " days : " + hours[4] + " hours");





    }



    @Override
    protected void onResume() {
        super.onResume();

        LeitnerCardList leitnerCardList;
        int[] readyWords = new int[5];
        int[] allWords = new int[5];

        for (int i = 0; i < 5; i++) {
            leitnerCardList = new LeitnerCardList(i + 1);
            readyWords[i] = leitnerCardList.getReadyCardNum();
            allWords[i] = leitnerCardList.getCount();
        }
        leitnerCardList = new LeitnerCardList(6);
        int allLearnedWordsNum = leitnerCardList.getCount();

        firstReadinessTextView.setText("ready: " + readyWords[0] + " / " + allWords[0]);
        secondReadinessTextView.setText("ready: " + readyWords[1] + " / " + allWords[1]);
        thirdReadinessTextView.setText("ready: " + readyWords[2] + " / " + allWords[2]);
        fourthReadinessTextView.setText("ready: " + readyWords[3] + " / " + allWords[3]);
        fifthReadinessTextView.setText("ready: " + readyWords[4] + " / " + allWords[4]);
        learnedNumTextView.setText("" + allLearnedWordsNum);



        int[] days = new int[5];
        int[] hours = new int[5];
        for (int i = 0; i < 5; i++) {
            int readyNum = new LeitnerCardList(i + 1, true).getReadyCardNum();

            leitnerCardList = new LeitnerCardList(i + 1, false);

            if(readyNum == 0) {
                long milliseconds = leitnerCardList.getMinMillisecondsTillReady();
                days[i] = LeitnerCardList.getDaysFromMilliseconds(milliseconds);
                hours[i] = LeitnerCardList.getHoursFromMilliseconds(milliseconds);
            } else {
                days[i] = 0;
                hours[i] = 0;
            }
        }

        firstDaysTextView.setText(days[0] + " days : " + hours[0] + " hours");
        secondDaysTextView.setText(days[1] + " days : " + hours[1] + " hours");
        thirdDaysTextView.setText(days[2] + " days : " + hours[2] + " hours");
        fourthDaysTextView.setText(days[3] + " days : " + hours[3] + " hours");
        fifthDaysTextView.setText(days[4] + " days : " + hours[4] + " hours");


    }



    @Override
    public void onClick(View v) {
        Intent nextActivityIntent = null;
        LeitnerCardList leitnerCardList;
        switch (v.getId()) {
            case R.id.addWords_button:
                openAddingWordDialog();
                return;
            case R.id.help_button:
                nextActivityIntent = new Intent(this, HelpActivity.class);
                break;
            case R.id.settings_button:
                nextActivityIntent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.firstBox_button:
                leitnerCardList = new LeitnerCardList(1);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, BoxActivity.class);
                    nextActivityIntent.putExtra("box_number", 1);
                } else
                    Toast.makeText(this, "There are no words ready for you to learn in this box!", Toast.LENGTH_LONG).show();
                break;
            case R.id.secondBox_button:
                leitnerCardList = new LeitnerCardList(2);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, BoxActivity.class);
                    nextActivityIntent.putExtra("box_number", 2);
                } else
                    Toast.makeText(this, "There are no words ready for you to learn in this box!", Toast.LENGTH_LONG).show();
                break;
            case R.id.thirdBox_button:
                leitnerCardList = new LeitnerCardList(3);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, BoxActivity.class);
                    nextActivityIntent.putExtra("box_number", 3);
                } else
                    Toast.makeText(this, "There are no words ready for you to learn in this box!", Toast.LENGTH_LONG).show();
                break;
            case R.id.fourthBox_button:
                leitnerCardList = new LeitnerCardList(4);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, BoxActivity.class);
                    nextActivityIntent.putExtra("box_number", 4);
                } else
                    Toast.makeText(this, "There are no words ready for you to learn in this box!", Toast.LENGTH_LONG).show();
                break;
            case R.id.fifthBox_button:
                leitnerCardList = new LeitnerCardList(5);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, BoxActivity.class);
                    nextActivityIntent.putExtra("box_number", 5);
                } else
                    Toast.makeText(this, "There are no words ready for you to learn in this box!", Toast.LENGTH_LONG).show();
                break;
            case R.id.allLearnedWords_button:
                leitnerCardList = new LeitnerCardList(6);
                if(leitnerCardList.getReadyCardNum() > 0) {
                    nextActivityIntent = new Intent(this, AllLearnedWordsActivity.class);
                } else
                    Toast.makeText(this, "You have not learned any words so far!", Toast.LENGTH_LONG).show();
                break;
        }

        if(nextActivityIntent != null)
            startActivity(nextActivityIntent);
    }


    private void openAddingWordDialog() {
        AddingWordDialog addingWordDialog = new AddingWordDialog();
        addingWordDialog.show(getSupportFragmentManager(), "adding word dialog");
    }


    @Override
    public void reloadReadinessOfFirstBox() {
        int firstBoxReadyWords;
        int firstBoxAllWords;
        LeitnerCardList leitnerCardList = new LeitnerCardList(1);
        firstBoxReadyWords = leitnerCardList.getReadyCardNum();
        firstBoxAllWords = leitnerCardList.getCount();

        firstReadinessTextView.setText("ready: " + firstBoxReadyWords+ " / " + firstBoxAllWords);
    }

}













