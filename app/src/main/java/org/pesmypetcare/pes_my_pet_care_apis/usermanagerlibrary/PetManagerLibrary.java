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
import java.util.concurrent.ExecutionException;

public class PetManagerLibrary extends AsyncTask<String, String, StringBuilder> {


    //"https://pes-my-pet-care.herokuapp.com/"
    /* /pet
    @PostMapping("/{owner}/{name}")
    post pet/{owner}/{name}
    delete pet/{owner}/{name}
    delete pet/{owner}
    get pet/{owner}/{name}
    get pet/{owner}
    get pet/{owner}/{name}/{field}
    put pet/{owner}/{name}/{field}*/


    private final String GET = "GET";
    private JSONObject postData;
    // private static String BASE_URL = "http://10.4.41.170:8081/";
    private static String BASE_URL = "https://pes-my-pet-care.herokuapp.com/pet/";

    private static int taskId;

    private PetManagerLibrary(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    public static void signUpPet(String username, String nameValuePost, Boolean sexValuePost, String raceValuePost, Date birthdayValuePost, double weightValuePost){ //username?
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValuePost);
        postData.put("sex", sexValuePost.toString());
        postData.put("race", raceValuePost);
        postData.put("birthday", birthdayValuePost.toString());
        postData.put("weight", Double.toString(weightValuePost));
        taskId = 0;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValuePost);
    }

    public static StringBuilder getPet(String username, String nameValueGet) throws ExecutionException, InterruptedException {
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValueGet);
        taskId = 1;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        return task.execute(BASE_URL + username + "/" + nameValueGet).get();
    }

    public static void deletePet(String username, String nameValueDelete){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValueDelete);
        taskId = 2;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValueDelete);
    }

    public static void updateSex(String username, String nameValuePut, Boolean sexValuePut){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValuePut);
        postData.put("sex", sexValuePut.toString());
        taskId = 3;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValuePut + "/" + sexValuePut);
    }

    public static void updateRace(String username, String nameValuePut, String raceValuePut){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValuePut);
        postData.put("race", raceValuePut);
        taskId = 4;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValuePut + "/" + raceValuePut);
    }

    public static void updateBirthday(String username, String nameValuePut, Date birthdayValuePut){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValuePut);
        postData.put("birthday", birthdayValuePut.toString());
        taskId = 5;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValuePut + "/" + birthdayValuePut);
    }

    public static void updateWeight(String username, String nameValuePut, double weightValuePut){
        Map<String, String> postData = new HashMap<>();
        postData.put("name", nameValuePut);
        postData.put("birthday", Double.toString(weightValuePut));
        taskId = 6;
        PetManagerLibrary task = new PetManagerLibrary(postData);
        task.execute(BASE_URL + username + "/" + nameValuePut + "/" + weightValuePut);
    }

    @Override
    protected StringBuilder doInBackground(String... params) {
        try {
            switch (taskId) {
                case 0:
                    postSignUpPet(params);
                    break;
                case 1:
                    return doGetPet(params);
                case 2:
                    deleteDeletePet(params);
                    break;
                case 3:
                    putUpdatePetSex(params);
                    break;
                case 4:
                    putUpdatePetRace(params);
                    break;
                case 5:
                    putUpdateBirthday(params);
                    break;
                case 6:
                    putUpdateWeight(params);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void postSignUpPet(String... params) throws IOException {
        URL url = new URL(params[0]);
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

    private StringBuilder doGetPet(String... params) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = getSimpleHttpUrlConnection(url, GET);
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

    private void deleteDeletePet(String... params) throws IOException {
        URL url = new URL(params[0]);
        HttpURLConnection con = makeConnection("DELETE", url);

        int responseCode = con.getResponseCode();
        System.out.println("DELETE Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) { //fail
            System.out.println("DELETE request not worked");
        }
    }

    private void putUpdatePetSex(String... params) throws IOException {
        URL url = new URL(params[0]);
        HttpURLConnection con = makeConnection("PUT", url);

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

    private void putUpdatePetRace(String... params) throws IOException {
        URL url = new URL(params[0]);
        HttpURLConnection con = makeConnection("PUT", url);

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

    private void putUpdateBirthday(String... params) throws IOException{
        URL url = new URL(params[0]);
        HttpURLConnection con = makeConnection("PUT", url);

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

    private void putUpdateWeight(String... params) throws IOException{
        URL url = new URL(params[0]);
        HttpURLConnection con = makeConnection("PUT", url);

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

    private HttpURLConnection getSimpleHttpUrlConnection(URL url, String method) throws JSONException, IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        return con;
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

}
