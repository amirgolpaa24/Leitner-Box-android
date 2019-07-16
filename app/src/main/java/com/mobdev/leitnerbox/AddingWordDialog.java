package com.mobdev.leitnerbox;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class AddingWordDialog extends AppCompatDialogFragment {

    private int permissionState = PERMISSION_NOT_GRANTED;
    private static final int PERMISSION_NOT_GRANTED = 0;
    private static final int PERMISSION_GRANTED = 1;

    private EditText wordInputEditText, meaningInputEditText;

    private AddingWordDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_addingworddialog, null);

        builder.setView(view)
                .setTitle("Add New Vocabulary")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



        FileInputStream fileInputStream = null;
        try {
            fileInputStream = Objects.requireNonNull(getContext()).openFileInput("permissionFile");
            permissionState = fileInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        wordInputEditText = view.findViewById(R.id.wordInput_editText);
        meaningInputEditText = view.findViewById(R.id.meaningInput_editText);


        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wantToCloseDialog = false;

                String wordString = wordInputEditText.getText().toString();
                String meaningString = meaningInputEditText.getText().toString();

                if(isStringNullOrWhiteSpace(wordString) || isStringNullOrWhiteSpace(meaningString)) {
                    Toast.makeText(getContext(), "Please fill both parts", Toast.LENGTH_LONG).show();
                } else {
                    addNewWordToFirstBox(wordString, meaningString);
                    listener.reloadReadinessOfFirstBox();
                    wantToCloseDialog = true;
                }

                if(wantToCloseDialog)
                    dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#218F07"));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#CF0808"));


        return dialog;

    }


    private void addNewWordToFirstBox(String newWord, String newMeaning) {
        if (permissionState == PERMISSION_NOT_GRANTED) {
            checkPermissions();
            //Toast.makeText(getContext(), "There is a problem with permission", Toast.LENGTH_SHORT).show();
            return;
        }

        new FileHelper().addNewWordToBoxFile(newWord, newMeaning, 1, false);
        Toast.makeText(getContext(), "word added", Toast.LENGTH_SHORT).show();

    }

    // Initiate request for permissions.
    private void checkPermissions() {
        if (!FileHelper.isExternalStorageReadable() || !FileHelper.isExternalStorageWritable()) {
            Toast.makeText(getContext(), "This app only works on devices with usable external storage", Toast.LENGTH_SHORT).show();
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FileHelper.REQUEST_PERMISSION_WRITE
            );
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FileHelper.REQUEST_PERMISSION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionState = PERMISSION_GRANTED;

                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = Objects.requireNonNull(getContext()).openFileOutput("permissionFile", Context.MODE_PRIVATE);
                    fileOutputStream.write(permissionState);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(fileOutputStream != null)
                            fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getContext(), "External storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "You must grant permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public interface AddingWordDialogListener {
        void reloadReadinessOfFirstBox();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddingWordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddingWordDialogListener");
        }
    }

}




















