package org.pesmypetcare.usermanagerlib;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskManager extends AsyncTask<String, String, StringBuilder> {
    private static TaskManager instance;
    private int taskId;
    private JSONObject reqBody;

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setReqBody(JSONObject reqBody) {
        this.reqBody = reqBody;
    }

    @Override
    protected StringBuilder doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        try {
            switch (taskId) {
                case 0:
                    doPost(params[0]);
                    break;
                case 1:
                    result = doGet(params[0]);
                    break;
                case 2:
                    doDelete(params[0]);
                    break;
                default:
                    doPut(params[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void doPost(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "POST");
        con.setDoOutput(true);
        writeRequestBody(con);

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        StringBuilder response;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = getResponseBody(con);
            System.out.println(response.toString());
        } else {
            response = getErrorResponseBody(con);
            System.out.println("POST request not worked: " + response.toString());
        }
    }

    private StringBuilder doGet(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = getResponseBody(con);
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        return response;
    }

    private void doDelete(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("DELETE Response Code :: " + responseCode);
        StringBuilder response;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = getResponseBody(con);
            System.out.println(response.toString());
        } else {
            System.out.println("DELETE request not worked");
        }
    }

    private void doPut(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "PUT");
        con.setDoOutput(true);
        writeRequestBody(con);

        int responseCode = con.getResponseCode();
        System.out.println("PUT Response Code :: " + responseCode);
        StringBuilder response;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = getResponseBody(con);
            System.out.println(response.toString());
        } else {
            System.out.println("PUT request not worked");
        }
    }

    private void writeRequestBody(HttpURLConnection con) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(reqBody.toString());
        writer.flush();
        writer.close();
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

    private StringBuilder getErrorResponseBody(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
            con.getErrorStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private HttpURLConnection getSimpleHttpUrlConnection(String targetUrl, String method) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        return con;
    }
}




