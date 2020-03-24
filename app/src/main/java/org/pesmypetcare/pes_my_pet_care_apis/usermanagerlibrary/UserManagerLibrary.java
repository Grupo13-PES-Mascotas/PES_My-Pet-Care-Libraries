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

public class UserManagerLibrary extends AsyncTask<String, String, StringBuilder> {

    private final String GET = "GET";
    private JSONObject postData;
    private static String BASE_URL = "http://10.4.41.170:8081/";

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

    public static String getUser(String usernameValueGet) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueGet);
        taskId = 1;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        return String.valueOf(task.execute("http://10.4.41.170:8081/users/"));
    }

    /*public static void getUser(Context context, String usernameValueGet, StringBuilder responseJson) {
        String url = BASE_URL + "users/" + usernameValueGet;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    responseJson.append(response.toString());
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseJson.append(new String(error.networkResponse.data));
            }
        });

        queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }*/

    public static void deleteUser(String usernameValueDelete) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueDelete);
        taskId = 2;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    public static void updatePassword(String usernameValueUpdatePwd, String pwdValueUpdate) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueUpdatePwd);
        postData.put("password", pwdValueUpdate);
        taskId = 3;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    public static void updateEmail(String usernameValueUpdateEmail, String emailValueUpdate) {
        Map<String, String> postData = new HashMap<>();
        postData.put("username", usernameValueUpdateEmail);
        postData.put("email", emailValueUpdate);
        taskId = 4;
        UserManagerLibrary task = new UserManagerLibrary(postData);
        task.execute("http://10.4.41.170:8081/users/");
    }

    @Override
    protected StringBuilder doInBackground(String... params) {
        try {
            switch (taskId) {
                case 0:
                    postSignUp(params);
                    break;
                case 1:
                    return doGet(params[0]);
                case 2:
                    deleteDeleteUser(params);
                    break;
                case 3:
                    putUpdateEmail(params);
                    break;
                case 4:
                    putUpdatePassword(params);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void postSignUp(String... params) throws IOException {
        URL url = null;
        try {
            url = new URL(params[0] + postData.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = makeConnection("POST", url);
        if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
            writer.close();
        }
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("POST request not worked");
        }
    }

    private StringBuilder doGet(String baseUrl) {
        StringBuilder response = new StringBuilder();
        try {
            HttpURLConnection con = getSimpleHttpUrlConnection(baseUrl, GET);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = getResponseBody(con);
                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpURLConnection getSimpleHttpUrlConnection(String baseUrl, String method) throws JSONException, IOException {
        String targetUrl = baseUrl + postData.getString("username");
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        return con;
    }

    private StringBuilder getResponseBody(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
            con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private void deleteDeleteUser(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("username") + "/delete"); //?
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = makeConnection("DELETE", obj);

        int responseCode = con.getResponseCode();
        System.out.println("DELETE Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("DELETE request not worked");
        }
    }

    private void putUpdateEmail(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("username") + "/update/email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = makeConnection("PUT", obj);

        if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
            writer.close();
        }

        int responseCode = con.getResponseCode();
        System.out.println("PUT Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("PUT request not worked");
        }
    }

    private void putUpdatePassword(String... params) throws IOException {
        URL obj = null;
        try {
            obj = new URL(params[0] + postData.getString("username") + "/update/password"); //?
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = makeConnection("PUT", obj);

        if (this.postData != null) {
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(postData.toString());
            writer.flush();
            writer.close();
        }

        int responseCode = con.getResponseCode();
        System.out.println("PUT Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("PUT request not worked");
        }
    }

    private HttpURLConnection makeConnection(String request, URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(request);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setDoInput(true);
        con.setDoOutput(true);
        return con;
    }

    private StringBuffer makeResponse(HttpURLConnection con) throws IOException {
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




