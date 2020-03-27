package org.pesmypetcare.usermanagerlib.clients;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskManager extends AsyncTask<String, String, StringBuilder> {
    private int taskId;
    private JSONObject reqBody;


    protected TaskManager() {
        taskId = -1;
        reqBody = new JSONObject();
    }

    /**
     * Set parameter taskId to identify the http request.
     * @param taskId The parameter to identify it
     */
    protected void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Set parameter reqBody to write in case of POST or PUT http request.
     * @param reqBody The parameter to write it
     */
    protected void setReqBody(JSONObject reqBody) {
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
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Make the POST request to URL specified.
     * @param targetUrl The URL where the request goes.
     * @throws IOException When Input or Outpul fails
     */
    private void doPost(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "POST");
        con.setDoOutput(true);
        writeRequestBody(con);

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("POST request not worked");
        }
    }

    /**
     * Make the GET request to URL specified.
     * @param targetUrl The URL where the request goes.
     * @return The request body.
     * @throws IOException When Input or Outpul fails
     */
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

    /**
     * Make the DELETE request to URL specified.
     * @param targetUrl The URL where the request goes.
     * @throws IOException When Input or Outpul fails
     */
    private void doDelete(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("DELETE Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("DELETE request not worked");
        }
    }

    /**
     * Make the PUT request to URL specified.
     * @param targetUrl The URL where the request goes.
     * @throws IOException When Input or Outpul fails
     */
    private void doPut(String targetUrl) throws IOException {
        HttpURLConnection con = getSimpleHttpUrlConnection(targetUrl, "PUT");
        con.setDoOutput(true);
        writeRequestBody(con);

        int responseCode = con.getResponseCode();
        System.out.println("PUT Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("PUT request not worked");
        }
    }

    /**
     * The method to write requests.
     * @param con The HttpURLConnection where the request goes.
     * @throws IOException When Input or Outpul fails
     */
    private void writeRequestBody(HttpURLConnection con) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(reqBody.toString());
        writer.flush();
        writer.close();
    }

     /**
      * The method to get the response of the request.
      * @param con The HttpURLConnection where the request comes from.
      * @return Return the body of the response.
      * @throws IOException When Input or Outpul fails
      */
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

    /**
     * Make the URL connection.
     * @param targetUrl The URL where we make the connection
     * @param method The type of request
     * @return Returns the connection made
     * @throws IOException When Input or Outpul fails
     */
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
