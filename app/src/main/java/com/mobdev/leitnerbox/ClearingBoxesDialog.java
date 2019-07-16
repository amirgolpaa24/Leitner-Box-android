package com.mobdev.leitnerbox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Objects;

public class ClearingBoxesDialog extends AppCompatDialogFragment {

    private ClearingAllFilesDialogListener listener;
    private SettingsActivity.ClearingType clearingType;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = null;
        if(clearingType == SettingsActivity.ClearingType.TYPE_CLEAR_BOX_FILES)
            view = inflater.inflate(R.layout.layout_clearingall5boxesdialog, null);
        else if (clearingType == SettingsActivity.ClearingType.TYPE_CLEAR_LEARNED_WORDS_FILE)
            view = inflater.inflate(R.layout.layout_clearinglearnedwordsboxdialog, null);

        String title = "";
        if(clearingType == SettingsActivity.ClearingType.TYPE_CLEAR_BOX_FILES)
            title = "Clearing All Five Boxes";
        else if (clearingType == SettingsActivity.ClearingType.TYPE_CLEAR_LEARNED_WORDS_FILE)
            title = "Clearing Learned Words Box";


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyClearing(clearingType);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#CF0808"));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1E091F"));

        return dialog;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ClearingAllFilesDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ClearingAllFilesDialogListener");
        }
    }

    public void setType(SettingsActivity.ClearingType clearingType) {
        this.clearingType = clearingType;
    }



    public interface ClearingAllFilesDialogListener {
        void applyClearing(SettingsActivity.ClearingType clearingType);
    }



}














