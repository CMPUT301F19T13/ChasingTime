package com.example.soulbook.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.soulbook.R;

/**
 * This s a class that implement delete photo function.
 */

public class deletePhotoDialog extends DialogFragment {
    private deletePhotoDialog.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onYesPressed(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof deletePhotoDialog.OnFragmentInteractionListener){
            listener = (deletePhotoDialog.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pass_add_friend_fragment, null);
        Bundle b = this.getArguments();
        final int position = b.getInt("position");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onYesPressed(position);
                    }}).create();
    }
}
