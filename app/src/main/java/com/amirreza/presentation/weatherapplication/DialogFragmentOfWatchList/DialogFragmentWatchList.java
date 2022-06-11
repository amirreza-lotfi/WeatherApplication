package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amirreza.presentation.weatherapplication.MainActivityViewModel;
import com.amirreza.domain.entity.WatchListWeather;
import com.amirreza.weatherapplication.R;
import com.google.android.material.button.MaterialButton;

public class DialogFragmentWatchList extends DialogFragment {
    private MainActivityViewModel mainActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View xmlLayout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_watch_list,null,false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return xmlLayout;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        WatchListWeather watchListWeather = mainActivityViewModel.getDeleteWatchList().getValue();


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View xmlLayout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_watch_list,null,false);
        alert.setView(xmlLayout);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        //city name
        TextView cityNameTv = xmlLayout.findViewById(R.id.dialog_city_name);
        String cityName =watchListWeather.getCityName()+"?";
        cityNameTv.setText(cityName);

        //cancel event
        MaterialButton cancelBtn = xmlLayout.findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            mainActivityViewModel.getDeleteWatchList().setValue(null);
            mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
            dismiss();
        });

        //delete event
        MaterialButton deleteBtn = xmlLayout.findViewById(R.id.dialog_delete_btn);
        deleteBtn.setOnClickListener(v -> {
            mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
            dismiss();
        });
        return alert.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivityViewModel.getDeleteWatchList().setValue(null);
        mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
    }
}