package com.example.raduc.travelapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlacesOptions;

import org.json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tab3fragment extends Fragment {
    private static final String TAG = "TaG3Fragment";

    List<String> listaMuzee = new ArrayList<String>();
    public ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arrayAdapter =  new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, listaMuzee);
        View view = inflater.inflate(R.layout.fragment_objectives,container,false);

        final Button button = view.findViewById(R.id.button_send);


        final ListView listView = view.findViewById(R.id.lista_muzee);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Context context = getContext();
                CharSequence text = "Searcing...";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                loadNearbyPlaces(44.378169, 26.135953, (ArrayList<String>) listaMuzee);

            }
        });

        listView.setAdapter(arrayAdapter);

        return view;
    }


    private void loadNearbyPlaces(double lat, double longitude,  final ArrayList<String> lista)
    {
        String placeType = "museum";
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(lat).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(5000);
        googlePlacesUrl.append("&types=").append(placeType);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyAjzIQsj9dsSjVKOmN73a5bUYDGryUIErw");


//        final JsonObjectRequest request;
//        request = new JsonObjectRequest(Request.Method.GET, googlePlacesUrl.toString(), null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("gplaces", "onResponse: Result= " + response.toString());
//                        parseLocationResult(response, lista);
//                       arrayAdapter.notifyDataSetChanged();
//                    }
//                },null);
        final Response.Listener<JSONObject> myListener = new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject results) {

                //Log.i("gplaces", "onResponse: Result= " + results.toString());
                parseLocationResult(results, lista);
                arrayAdapter.notifyDataSetChanged();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),

                null,

                myListener,

                new Response.ErrorListener() {
                    @Override                    public void onErrorResponse(VolleyError error) {
                        Log.e("gplaces", "onErrorResponse: Error= " + error);
                        Log.e("gplaces", "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(request);

    }

    private void parseLocationResult(JSONObject result, ArrayList<String> lista) {

        String id, place_id, placeName = null, reference, icon, vicinity = null;
        double latitude, longitude;

        try {
            JSONArray jsonArray = result.getJSONArray("request");

            if (result.getString("status").equalsIgnoreCase("OK")) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    id = place.getString("id");
                    place_id = place.getString("place_id");
                    if (!place.isNull("name")) {
                        placeName = place.getString("name");
                    }
                    if (!place.isNull("vicinity")) {
                        vicinity = place.getString("vicinity");
                    }
                    latitude = place.getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");
                    longitude = place.getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");
                    reference = place.getString("reference");
                    icon = place.getString("icon");

                    //Aici faci ce vrei cu datele de mai sus
                    lista.add(placeName);
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }

}

