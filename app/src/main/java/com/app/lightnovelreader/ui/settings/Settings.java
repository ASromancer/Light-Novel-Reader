package com.app.lightnovelreader.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.FragmentSettingsBinding;
import com.app.lightnovelreader.ui.authentication.LoginActivity;
import com.app.lightnovelreader.ui.authentication.modal.ChangePasswordModal;
import com.google.firebase.auth.FirebaseAuth;


public class Settings extends Fragment {

    private FragmentSettingsBinding binding;

    public Settings() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        binding.btnLogout.setOnClickListener(view -> {
            mFirebaseAuth.signOut();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        });

        binding.btnChangePassword.setOnClickListener(view -> {
            ChangePasswordModal modal = new ChangePasswordModal();
            modal.show(getParentFragmentManager(), "change_password_modal");
        });



        return binding.getRoot();
    }
}
