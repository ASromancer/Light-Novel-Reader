package com.app.lightnovelreader.ui.authentication.modal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.ui.authentication.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordModal extends DialogFragment {
    private EditText oldPasswordEditText, newPasswordEditText;
    protected FirebaseAuth mFirebaseAuth;
    private Button changePasswordButton;
    private String apiResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_change_password, container, false);
        oldPasswordEditText = view.findViewById(R.id.old_password);
        newPasswordEditText = view.findViewById(R.id.new_password);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        mFirebaseAuth = FirebaseAuth.getInstance();
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                if(newPassword == oldPassword){
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                                    } else {
                                        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    dismiss();
                }

            }
        });
        return view;
    }
}
