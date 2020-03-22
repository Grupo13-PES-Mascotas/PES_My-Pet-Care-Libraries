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
import java.util.HashMap;
import java.util.Map;

public class UserManagerLibrary extends AsyncTask<String, String, String> {



    JSONObject postData;

    private static final String USER_AGENT = "Mozilla/5.0";

    private static int task_id;

    public UserManagerLibrary(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    public static void AltaUsuari (String username, String password, String email) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", username);
        postData.put("email", email);
        postData.put("password",password);
        task_id = 0;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("https://pes-my-pet-care.herokuapp.com/signup?password=");
    }

    public static void ConsultarUsuari (String username) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", username);
        task_id = 1;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (task_id == 0) {
                Post_AltaUsuari(params);
            }
            else {
                Get_Usuari(params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void Post_AltaUsuari(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setDoInput(true);
        con.setDoOutput(true);

        if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
        }

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }

    public String Get_Usuari(String... params) throws IOException {
        System.out.println("HOLAAAAAAAAAAA");
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        System.out.println("HOLAAAAAAAAAAA");

        con.setRequestMethod("GET");
        System.out.println("HOLoooooooooooooooooo");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setDoInput(true);
        con.setDoOutput(true);

        /*if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
        }*/

        int responseCode = con.getResponseCode();
        System.out.println("HOLtttttttttttttttt");
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
            return response.toString();
            // print result
        } else {
            System.out.println("GET request not worked");
        }
        return null;
    }
}
