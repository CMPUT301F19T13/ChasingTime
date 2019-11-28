package com.example.soulbook.ui.dashboard;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.soulbook.R;

/**
 * This is the class that used to build add friends interface. 
 * It's not complete.
 */

public class addFriendFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    TextView nickname;
    EditText message;

    public interface OnFragmentInteractionListener {
        void onYesPressed(String message, String userId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_friend_fragment, null);
        nickname = view.findViewById(R.id.add_friend_nickname);
        message = view.findViewById(R.id.add_friend_message);
        Bundle b = this.getArguments();
        nickname.setText(b.get("nickname").toString());
        final String userId = b.get("userId").toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Send add friend requirement")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(message.getText() != null){
                            listener.onYesPressed(message.getText().toString(), userId);
                        }
                        else{
                            listener.onYesPressed("", userId);
                        }

                    }}).create();
    }
}
