package com.example.soulbook.ui.notifications;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.soulbook.R;
import com.example.soulbook.datasave;
import com.example.soulbook.ui.dashboard.searchFriend;
import com.google.firebase.database.FirebaseDatabase;

public class setNicknameFragment extends DialogFragment {

    EditText setNewNickname;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_set_new_nickname, null);
        setNewNickname = view.findViewById(R.id.setnickname);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (setNewNickname.getText().toString() != ""){
                            FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("nickname").setValue(setNewNickname.getText().toString());
                            datasave.thisuser.setNickname(setNewNickname.getText().toString());
                        }
                       else{
                            Toast.makeText(getContext(), "invalid nickname", Toast.LENGTH_LONG).show();
                        }
                    }}).create();
    }
}
