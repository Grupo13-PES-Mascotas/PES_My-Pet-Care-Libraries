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

    /*
     *Creation class
     */
    public TaskManager() {
        taskId = -1;
        reqBody = null;
    }

    /*
     *Sets the local taskId to a new taskId
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /*
     *Sets the request body to a new request body
     */
    public void setReqBody(JSONObject reqBody) {
        this.reqBody = reqBody;
    }

    /*
     *Function automatically called once the task is created and is run with "execute"
     * There can't be more than one task running on the same TaskManagers, but this is already
     * managed through the ManagerClients.
     * @return Returns a StringBuilder for the Get's. Returns nothing for the rest.
     */
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

    /*
    *Function to post some info in certain url
    * @return void
     */
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

    /*
    * Method that performs the GET operation from a certain URL
    * @return Info stored in that URL in the shape of a StringBuilder
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

    /*
     * Method that performs the DELETE operation from a certain URL
     * @return void
     */
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

    /*
     * Method that performs the PUT operation from a certain URL
     * @return void
     */
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

    /*
     * Method that fills the Body of a request
     * @return void
     */
    private void writeRequestBody(HttpURLConnection con) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(reqBody.toString());
        writer.flush();
        writer.close();
    }

    /*
     * Method that fills the Body of a response
     * @return void
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

    /*
     * Method that gets the body of an error
     * @return void
     */
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

    /*
     * Method that stablishes an HTTP connection with the provided URL with the method
     * @return void
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




