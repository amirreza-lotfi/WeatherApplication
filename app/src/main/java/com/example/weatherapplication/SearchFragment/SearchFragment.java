package com.example.weatherapplication.SearchFragment;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.weatherapplication.AllCityInTheWorld;
import com.example.weatherapplication.CityFragment.CityFragment;
import com.example.weatherapplication.MainActivityViewModel;
import com.example.weatherapplication.R;
import com.example.weatherapplication.WatchList.WatchListDouInterface;
import com.example.weatherapplication.WatchList.WatchListWeather;

import static android.view.View.GONE;

public class SearchFragment extends Fragment{
    private AppCompatImageView backButton;
    private AutoCompleteTextView searchTextView;
    private SearchTextViewAdapter adapter;
    private MainActivityViewModel mainActivityViewModel;
    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_search, container, false);
        backButton = layout.findViewById(R.id.search_fragment_back);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        setAutoCompleteSearch(view);

        chooseSuggestion();

        View view3 = view.findViewById(R.id.free_space_suggestion);
        view3.setOnClickListener(view1 -> searchTextView.showDropDown());

        backButtonManaging();
    }

    public void setAutoCompleteSearch(View view){
        AllCityInTheWorld suggestionCities = new AllCityInTheWorld(getContext());
        searchTextView = view.findViewById(R.id.search_fragment_city_name);
        adapter = new SearchTextViewAdapter(getContext(),suggestionCities.getAllCitiesInTheWorld());
        searchTextView.setAdapter(adapter);
    }

    public void chooseSuggestion(){
        searchTextView.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = getCityNameFromAutoCompleteTextView(view);
            double lat = getLatFromInformation(view);
            double lon = getLonFromInformation(view);
            String country = getCountryName(view);

            setAutoCompleteSearchTextView(cityName);
            mainActivityViewModel.setCityInformation(lat,lon,cityName,country);
            mainActivityViewModel.getIsNavigateFromSearchFragment().setValue(true);
            Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_searchFragment_to_cityFragment2);

        });
        searchTextView.setOnKeyListener((v, keyCode, event) -> false);

    }

    public String getCityNameFromAutoCompleteTextView(View view){
        TextView cityDataInfoTV = view.findViewById(R.id.search_suggestion_city_name);
        return cityDataInfoTV.getText().toString().split("!")[0];
    }

    public double getLatFromInformation(View view){
        TextView latTv = view.findViewById(R.id.search_suggestion_city_other_information);
        return Double.parseDouble(latTv.getText().toString().split("/")[1].split(",")[0]);
    }

    public double getLonFromInformation(View view){
        TextView lonTv = view.findViewById(R.id.search_suggestion_city_other_information);
        return Double.parseDouble(lonTv.getText().toString().split("/")[1].split(",")[1]);
    }

    public String getCountryName(View view){
        TextView country = view.findViewById(R.id.search_suggestion_city_other_information);
        return country.getText().toString().split("/")[0];
    }

    public void setAutoCompleteSearchTextView(String cityName){
        searchTextView.setText(cityName);
    }

    public void backButtonManaging(){
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }
}
