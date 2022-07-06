package com.amirreza.presentation.weatherapplication.SearchFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.amirreza.domain.entity.CityEntity
import com.amirreza.common.AllCityInTheWorld
import com.amirreza.domain.entity.WatchListWeather
import com.amirreza.presentation.weatherapplication.CityFragment.CityFragmentViewModel
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.FragmentSearchBinding
import com.amirreza.weatherapplication.databinding.SearchSeggestionItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SearchFragment:Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val cityFragmentViewModel by sharedViewModel<CityFragmentViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setAutoCompleteSearch()
        chooseSuggestion()
        binding.freeSpaceSuggestion.setOnClickListener{
            binding.searchFragmentCityName.showDropDown()
        }
        backButtonManaging()
    }
    private fun chooseSuggestion(){
        binding.searchFragmentCityName.setOnClickListener { selectedItem ->
            val binding = SearchSeggestionItemBinding.bind(selectedItem)

            val cityName: String = binding.searchSuggestionCityName.text as String
            val country: String = getCountryName(selectedItem)
            val lat = getLatFromInformation(selectedItem)
            val lon = getLonFromInformation(selectedItem)

            setAutoCompleteSearchTextView(country)


            cityFragmentViewModel
                .addCityToDatabaseFromSearchFragment(
                    WatchListWeather(
                        cityName = cityName,
                        country = country,
                        description = "",
                        temperature = "",
                        weatherImagePath = "",
                        lat = lat,
                        lon = lon
                    )
                )



            findNavController(requireView()).navigate(R.id.action_searchFragment_to_cityFragment2)
        }
    }




    private fun setAutoCompleteSearch() {
        val suggestionCities =
            AllCityInTheWorld(
                context
            )
        val adapter = SearchTextViewAdapter(requireContext(), suggestionCities.allCitiesInTheWorld)
        binding.searchFragmentCityName.setAdapter(adapter)
    }



    private fun getLatFromInformation(view: View): Double {
        val latTv = view.findViewById<TextView>(R.id.search_suggestion_city_other_information)
        return latTv.text.toString().split("/").toTypedArray()[1].split(",")
            .toTypedArray()[0].toDouble()
    }
    private fun getLonFromInformation(view: View): Double {
        val lonTv = view.findViewById<TextView>(R.id.search_suggestion_city_other_information)
        return lonTv.text.toString().split("/").toTypedArray()[1].split(",")
            .toTypedArray()[1].toDouble()
    }
    private fun getCountryName(view: View): String {
        val country = view.findViewById<TextView>(R.id.search_suggestion_city_other_information)
        return country.text.toString().split("/").toTypedArray()[0]
    }
    private fun setAutoCompleteSearchTextView(cityName: String?) {
        binding.searchFragmentCityName.setText(cityName)
    }
    private fun backButtonManaging() {
        binding.searchFragmentBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}