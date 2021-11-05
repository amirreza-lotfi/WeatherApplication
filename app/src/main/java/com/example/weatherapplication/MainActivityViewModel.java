package com.example.weatherapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapplication.CityFragment.CityInformation;
import com.example.weatherapplication.CityFragment.CityWeatherDataInOneCall;
import com.example.weatherapplication.WatchList.WatchListWeather;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    private CityRepository cityRepository;
    private Disposable disposable;
    private MutableLiveData<CityWeatherDataInOneCall> cityWeatherDataInOneCallMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CityInformation> cityInformation = new MutableLiveData<>();
    private MutableLiveData<String> noConnectionError = new MutableLiveData<>();
    private MutableLiveData<String> reloadPage = new MutableLiveData<>();
    private MutableLiveData<Boolean> rotate = new MutableLiveData<>(false);
    private MutableLiveData<WatchListWeather> watchListItemSelected = new MutableLiveData<>();
    private MutableLiveData<WatchListWeather> deleteWatchList = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToDialogDeleteWatchList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNavigateFromSearchFragment = new MutableLiveData<>();
    private MutableLiveData<Boolean> isWatchListItemSelect = new MutableLiveData<>();

    public MainActivityViewModel(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        List<WatchListWeather> watchListWeathers = getCityDataFromDatabase();
        if(watchListWeathers!=null && watchListWeathers.size()!=0) {
            WatchListWeather watchListWeather = getCityDataFromDatabase().get(0);
            cityInformation.setValue(new CityInformation(watchListWeather.getLat(),watchListWeather.getLon(),watchListWeather.getCityName(),watchListWeather.getCountry()));
        }
        else{
            cityInformation.setValue(null);
        }
        navigateToDialogDeleteWatchList.setValue(false);
        isNavigateFromSearchFragment.setValue(false);
        isWatchListItemSelect.setValue(false);
        cityWeatherDataInOneCallMutableLiveData.setValue(null);
        watchListItemSelected.setValue(null);
    }


    public List<WatchListWeather> getCityDataFromDatabase() {
        return cityRepository.getCityDataFromDatabase();
    }

    public void setCityInformation(double lat, double lon, String city, String country) {
        cityInformation.setValue(new CityInformation(lat, lon, city, country));
    }

    public MutableLiveData<CityInformation> getCityInformation() {
        return cityInformation;
    }

    public void getCityDataFromServer() {

        double lat = cityInformation.getValue().getLat();
        double lon = cityInformation.getValue().getLon();
        cityRepository.getCityDataFromServer(lat,lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> rotate.postValue(false))
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        CityWeatherDataInOneCall cityWeatherDataINOneCall = new CityWeatherDataInOneCall(s);
                        cityWeatherDataInOneCallMutableLiveData.postValue(cityWeatherDataINOneCall);
                        noConnectionError.postValue("Connected");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        noConnectionError.postValue("No Connection");
                        Log.i("serverV", "onError: "+"connection fail");
                    }
                });
    }

    public MutableLiveData<CityWeatherDataInOneCall> getCityWeatherDataInOneCallMutableLiveData() {
        return cityWeatherDataInOneCallMutableLiveData;
    }

    public void addWatchListWeather(WatchListWeather watchListWeather){
        cityRepository.addWatchListWeather(watchListWeather);
    }

    public void deleteWatchList(WatchListWeather watchListWeather){
        cityRepository.deleteWatchList(watchListWeather);
    }


    public MutableLiveData<String> getNoConnectionError() {
        return noConnectionError;
    }

    public MutableLiveData<String> getReloadPage() {
        return reloadPage;
    }

    public MutableLiveData<Boolean> getRotate() {
        return rotate;
    }

    public MutableLiveData<WatchListWeather> getWatchListItemSelected() {
        return watchListItemSelected;
    }

    public MutableLiveData<WatchListWeather> getDeleteWatchList() {
        return deleteWatchList;
    }

    public MutableLiveData<Boolean> getNavigateToDialogDeleteWatchList() {
        return navigateToDialogDeleteWatchList;
    }

    public MutableLiveData<Boolean> getIsNavigateFromSearchFragment() {
        return isNavigateFromSearchFragment;
    }

    public MutableLiveData<Boolean> getIsWatchListItemSelect() {
        return isWatchListItemSelect;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
