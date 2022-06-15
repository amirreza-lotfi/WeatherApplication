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
    private val cityFragmentViewModel by sharedViewModel<CityFragmentViewModel>()

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
        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        WatchListWeather watchListWeather = mainActivityViewModel.getDeleteWatchList().getValue();
//
//        //city name
//        TextView cityNameTv = xmlLayout.findViewById(R.id.dialog_city_name);
//        String cityName =watchListWeather.getCityName()+"?";
//        cityNameTv.setText(cityName);
//
//        //cancel event
//        MaterialButton cancelBtn = xmlLayout.findViewById(R.id.dialog_cancel_btn);
//        cancelBtn.setOnClickListener(v -> {
//            mainActivityViewModel.getDeleteWatchList().setValue(null);
//            mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
//            dismiss();
//        });
//
//        //delete event
//        MaterialButton deleteBtn = xmlLayout.findViewById(R.id.dialog_delete_btn);
//        deleteBtn.setOnClickListener(v -> {
//            mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
//            dismiss();
//        });
//        return alert.create();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mainActivityViewModel.getDeleteWatchList().setValue(null);
//        mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(false);
//    }

    override fun onClick(watchListWeather: WatchListWeather) {
        //todo delete watchlist from viewModel
    }

    override fun onLongClick(watchListWeather: WatchListWeather) {
        cityFragmentViewModel.transactionToDialogFragment.value = true
        cityFragmentViewModel.wantToDeleteCity = watchListWeather
    }
}