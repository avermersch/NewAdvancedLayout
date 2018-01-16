package com.example.formation.newadvancedlayout;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.formation.newadvancedlayout.model.RandomUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * A simple {@link Fragment} subclass.
 */
public class RandomUserFragment extends Fragment {

   private List<RandomUser> userList;

    public RandomUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDataFromHttp();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_user, container, false);
    }

    private void getDataFromHttp(){
        String url= "https://jsonplaceholder.typicode.com/users";

        //Définition de la requête
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTP", response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("HTTP",error.getMessage());
                    }
                }
        );

        //Ajouter la requête à la file d'exécution
        Volley  .newRequestQueue(this.getActivity())
                .add(request);
    }

    //Conversion d'une réponse JSON(chaine de caractère)
    //en une liste RandomUser
    private List<RandomUser> responseToList(String response){
        List<RandomUser> list = new ArrayList<>();

        try {
            JSONArray jsonUsers = new JSONArray(response);
            JSONObject item;
            for(int i = 0; i < jsonUsers.length(); i++){
                item = (JSONObject) jsonUsers.get(i);

                //Création d'un nouvel utilisateur
                RandomUser user = new RandomUser();

                //Hydratation de l'utilisateur
                user.setName(item.getString("name"));
                user.setEmail(item.getString("email"));

                JSONObject geo = item.getJSONObject("geo");

                user.setLatitude(geo.getDouble("lat"));
                user.setLongitude(geo.getDouble("lng"));

                //Ajout de l'utilisateur à la liste
                list.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
