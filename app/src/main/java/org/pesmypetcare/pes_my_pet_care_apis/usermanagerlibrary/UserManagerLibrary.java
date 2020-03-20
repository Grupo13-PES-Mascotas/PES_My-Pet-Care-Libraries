package org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserManagerLibrary {

    //1. Funcio petita amb atribut corresponent
    //2. Definir petició amb constructora
    //3. Request amb id_peticio
    //4. Request real amb setRequestMethod

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String user_id = "145";

    private static final String GET_URL = "10.4.41.170:8080/singup";

    private static final String username = "username=Pankaj";

    private static final String password = "password=123456";

    private static final String email = "email=pankaj@gmail.com";

    public static void PostUser(String username, String password, String email) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("username", username);
        con.setRequestProperty("password", password);
        con.setRequestProperty("email", email);
        //Map<String,String> UserParams= new HashMap<>();

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(username.getBytes());
        os.write(password.getBytes());
        os.write(email.getBytes());
        os.flush();
        os.close();

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

    public static StringBuffer GetUser(String user_id) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("GetUser", user_id);
        //Map<String,String> UserParams= new HashMap<>();

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
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
            System.out.println("GET request not worked");
        }
        return response;
    }


    /*public void ejecutarHttpAsyncTask() {
        if (1 == 0) new HttpAsyncTask().execute("http://10.4.41.168:5000/users");
    }*/

    /*private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            if (peticion_id < 10 || peticion_id == 21 || peticion_id == 22 || peticion_id == 23 || peticion_id == 27 || peticion_id == 29 || peticion_id == 31
                    || peticion_id == 99 || peticion_id == 98 || peticion_id == 33 || peticion_id == 34 || peticion_id == 40 || peticion_id == 41)
                return GET(urls[0]);
            else if ( (peticion_id >= 10 && peticion_id < 15) || peticion_id == 30 || peticion_id == 32 ||peticion_id == 35)
                return POST(urls[0]);
            else if ( (peticion_id >= 15 && peticion_id < 20) || peticion_id == 24 || peticion_id == 26 || peticion_id == 28) return PUT(urls[0]);
            else return DELETE(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(MainActivity.contexto, "Received!", Toast.LENGTH_LONG).show();
        }
    }*/




}
