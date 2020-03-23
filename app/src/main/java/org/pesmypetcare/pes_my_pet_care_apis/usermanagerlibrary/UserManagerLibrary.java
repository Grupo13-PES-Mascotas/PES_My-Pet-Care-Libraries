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



    private JSONObject postData;

    private static int taskId;

    private UserManagerLibrary(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    public static void signUp(String usernameValueSign, String passwordValueSign, String emailValueSign) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueSign);
        postData.put("email", emailValueSign);
        postData.put("password", passwordValueSign);
        taskId = 0;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("https://pes-my-pet-care.herokuapp.com/signup?password=");
    }

    public static void getUser(String usernameValueGet) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueGet);
        taskId = 1;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (taskId == 0) {
                postSignUp(params);
            } else {
                String response = getGetUser(params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void postSignUp(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = makeConnection("POST", obj);
        if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("POST request not worked");
        }
    }

    public String getGetUser(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = makeConnection("GET", obj);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            StringBuffer response = makeResponse(con);
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        return null;
    }

    public HttpURLConnection makeConnection(String request, URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(request);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setDoInput(true);
        con.setDoOutput(true);
        return con;
    }
    public StringBuffer makeResponse(HttpURLConnection con) throws IOException {
        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine = in.readLine();

        while (inputLine != null) {
            response.append(inputLine);
            response.append("\n");
            inputLine = in.readLine();
        }
        in.close();
        return response;
    }

}
