package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.amirreza.domain.entity.WatchListWeather;
import com.amirreza.presentation.weatherapplication.CityFragment.CityFragmentViewModel
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnDialogActions
import com.amirreza.weatherapplication.databinding.FragmentDialogWatchListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogFragmentWatchList(
    private val onDialogActions: OnDialogActions,
    private val watchListWeather: WatchListWeather
) : DialogFragment() {
    private lateinit var binding: FragmentDialogWatchListBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogWatchListBinding.inflate(layoutInflater, null, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.dialogDeleteBtn.setOnClickListener {
            onDialogActions.onDeleteClicked(watchListWeather)
        }
        binding.dialogCancelBtn.setOnClickListener {
            onDialogActions.onCancelClicked(watchListWeather)
        }
        binding.dialogCityName.text = watchListWeather.cityName

        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
    }
}