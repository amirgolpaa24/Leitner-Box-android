package com.mobdev.leitnerbox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Objects;

public class DeletingWordDialog extends DialogFragment {

    private DeletingWordDialog.DeletingWordDialogListener listener;

    private WordFragment wordFragment;


    public DeletingWordDialog() {
    }

    public void setWordFragment(WordFragment wordFragment) {
        this.wordFragment = wordFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_deletingworddialog, null);

        builder.setView(view)
                .setTitle("Deleting this Word")
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyDeleting(wordFragment);
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
            listener = (DeletingWordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DeletingWordDialogListener");
        }
    }

    public interface DeletingWordDialogListener {
        void applyDeleting(WordFragment wordFragment);
    }



}
