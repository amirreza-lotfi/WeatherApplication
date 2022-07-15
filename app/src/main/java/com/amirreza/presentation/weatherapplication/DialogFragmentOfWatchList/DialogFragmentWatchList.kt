package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.amirreza.domain.entity.SavedCityWeather;
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnDialogActions
import com.amirreza.weatherapplication.databinding.FragmentDialogWatchListBinding

class DialogFragmentWatchList(
    private val onDialogActions: OnDialogActions,
    private val savedCityWeather: SavedCityWeather
) : DialogFragment() {
    private lateinit var binding: FragmentDialogWatchListBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogWatchListBinding.inflate(layoutInflater, null, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.dialogDeleteBtn.setOnClickListener {
            onDialogActions.onDeleteClicked(savedCityWeather)
        }
        binding.dialogCancelBtn.setOnClickListener {
            onDialogActions.onCancelClicked(savedCityWeather)
        }
        binding.dialogCityName.text = savedCityWeather.cityName

        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
    }
}