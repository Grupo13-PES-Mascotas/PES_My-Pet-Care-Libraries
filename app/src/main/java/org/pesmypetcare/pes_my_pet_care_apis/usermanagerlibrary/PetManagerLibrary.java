package org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PetManagerLibrary extends AsyncTask<String, String, StringBuilder> {

    private final String GET = "GET";
    private JSONObject postData;
    private static String BASE_URL = "http://10.4.41.170:8081/";

    private static int taskId;

    private PetManagerLibrary(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    public static void signUpPet(String name, Boolean sex, String race, Date birthday, double weight){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", name);
        postData.put("sex", sex.toString());
        postData.put("race", race);
        postData.put("birthday", birthday.toString());
        postData.put("weight", weight);
        taskId = 0;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute("https://pes-my-pet-care.herokuapp.com/signup?password=");
    }

    public static void getPet(String name){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", name);
        taskId = 1;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    public static void deletePet(String name){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", name);
        taskId = 2;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    public static void updateGender(String name, Boolean sex){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", name);
        postData.put("sex", sex.toString());
        taskId = 3;
        UserManagerLibrary task = new PetManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");

    }

    public static void updateRace(){

    }

    public static void updateBirthday(){

    }

    public static void updateWeight(){

    }


    @Override
    protected StringBuilder doInBackground(String... strings) {
        return null;
    }
}
