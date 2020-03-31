package com.belfoapps.synonymsquiz.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.belfoapps.synonymsquiz.pojo.Synonym;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferencesHelper {
    private static final String DARK_MODE = "Dark Mode";
    private static final String SYNONYM = "Synonym";

    /************************************* Declarations *******************************************/
    private SharedPreferences sharedPref;
    private Gson gson;
    private Context context;

    /************************************* Constructor ********************************************/
    public SharedPreferencesHelper(Context context) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences("BASICS", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /************************************* Methods ***********************************************/

    public String getJsonFileFromAssets(String filename) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private boolean isEmpty(String mode) {
        return sharedPref.getString(mode, null) == null;
    }

    public ArrayList<Synonym> getSynonyms() {
        if (isEmpty(SYNONYM)) return null;
        else {
            String jsonSynonyms = sharedPref.getString(SYNONYM, null);
            Synonym[] synonymItem = gson.fromJson(jsonSynonyms, Synonym[].class);
            return new ArrayList<>(Arrays.asList(synonymItem));
        }
    }

    public void saveSynonyms(ArrayList<Synonym> shoppings) {
        String jsonToString = gson.toJson(shoppings);
        SharedPreferences.Editor editor;
        editor = sharedPref.edit();
        editor.putString(SYNONYM, jsonToString).apply();
    }


    /************************************* Extra Methods ******************************************/
    //Dark Mode
    public void setDarkModeEnable(boolean isEnabled) {
        SharedPreferences.Editor editor;
        editor = sharedPref.edit();
        editor.putBoolean(DARK_MODE, isEnabled).apply();
    }

    public boolean isDarkModeEnabled() {
        return sharedPref.getBoolean(DARK_MODE, false);
    }

}
