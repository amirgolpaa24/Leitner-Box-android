package com.mobdev.leitnerbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity implements ClearingBoxesDialog.ClearingAllFilesDialogListener {

    private Button clearAllBoxFilesButton, clearLearnedWordsFileButton, backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        clearAllBoxFilesButton = findViewById(R.id.clearAllBoxes_button);
        clearAllBoxFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClearingAllFilesDialog(ClearingType.TYPE_CLEAR_BOX_FILES);
            }
        });


        clearLearnedWordsFileButton = findViewById(R.id.clearLearnedWordsBox_button);
        clearLearnedWordsFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClearingAllFilesDialog(ClearingType.TYPE_CLEAR_LEARNED_WORDS_FILE);
            }
        });


        backButton = findViewById(R.id.settingsactivity_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void openClearingAllFilesDialog(ClearingType clearingType) {
        ClearingBoxesDialog clearingAllFilesDialog = new ClearingBoxesDialog();
        clearingAllFilesDialog.setType(clearingType);
        clearingAllFilesDialog.show(getSupportFragmentManager(), "clearing all files dialog");
    }


    @Override
    public void applyClearing(ClearingType clearingType) {
        if(clearingType == ClearingType.TYPE_CLEAR_BOX_FILES) {
            File f;
            FileOutputStream fileOutputStream = null;
            for (int i = 1; i <= 5; i++) {
                f = FileHelper.getBoxFile(i);
                try {
                    fileOutputStream = new FileOutputStream(f);
                    fileOutputStream.write("".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null)
                            fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        else if(clearingType == ClearingType.TYPE_CLEAR_LEARNED_WORDS_FILE) {
            File f;
            FileOutputStream fileOutputStream = null;
            int i = 6;

            f = FileHelper.getBoxFile(i);
            try {
                fileOutputStream = new FileOutputStream(f);
                fileOutputStream.write("".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public enum ClearingType {
        TYPE_CLEAR_BOX_FILES,
        TYPE_CLEAR_LEARNED_WORDS_FILE
    }



}



