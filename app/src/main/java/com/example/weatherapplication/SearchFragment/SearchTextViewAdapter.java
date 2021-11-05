package com.example.weatherapplication.SearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SearchTextViewAdapter extends ArrayAdapter<String> {
    private final List<String> citiesName;
    private List<String> supportCitiesNameWhenSuggestionEmpty;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_seggestion_item,parent,false);
        String[]cityAndCountry = getItem(position).split("!");

        TextView cityName = convertView.findViewById(R.id.search_suggestion_city_name);
        TextView cityData = convertView.findViewById(R.id.search_suggestion_city_other_information);

        cityName.setText(cityAndCountry[0]);
        cityData.setText(cityAndCountry[1]+"/ "+cityAndCountry[2]+" , "+cityAndCountry[3]);
        return convertView;
    }

    public SearchTextViewAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
        citiesName = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filteringCity;
    }


    private Filter filteringCity = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> suggestions = new ArrayList<>();
            if(constraint == null || constraint.length() ==0){
                suggestions.addAll(citiesName);
            }
            else {
                String filteredInput = constraint.toString().toLowerCase();
                for(String item: citiesName){

                    if(item.toLowerCase().startsWith(filteredInput)) {
                        suggestions.add(item);
                        if(suggestions.size()>100)
                            break;
                    }
                }
            }
            if(suggestions.size()!=0)
                supportCitiesNameWhenSuggestionEmpty = new ArrayList<>(suggestions);
            else
                suggestions = new ArrayList<>(supportCitiesNameWhenSuggestionEmpty);



            FilterResults filterResults = new FilterResults();

            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


}
