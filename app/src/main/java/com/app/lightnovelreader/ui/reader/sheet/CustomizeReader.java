package com.app.lightnovelreader.ui.reader.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.lightnovelreader.App;
import com.app.lightnovelreader.config.Ranobe;
import com.app.lightnovelreader.databinding.SheetCustomizeReaderBinding;
import com.app.lightnovelreader.ui.reader.sheet.adapter.ReaderThemeAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomizeReader extends BottomSheetDialogFragment implements ReaderThemeAdapter.OnReaderThemeSelected {
    private final OnOptionSelection listener;

    public CustomizeReader(OnOptionSelection listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheetCustomizeReaderBinding binding = SheetCustomizeReaderBinding.inflate(inflater, container, false);

        binding.fontSlider.setValue(Ranobe.getReaderFont(App.getContext()));
        binding.fontSlider.addOnChangeListener((slider, value, fromUser) -> listener.setFontSize(value));

        binding.readerThemeList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.readerThemeList.setAdapter(new ReaderThemeAdapter(this));
        return binding.getRoot();
    }

    @Override
    public void select(String themeName) {
        listener.setReaderTheme(themeName);
    }

    public interface OnOptionSelection {
        void setFontSize(float size);

        void setReaderTheme(String themeName);
    }
}
