package org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary;


import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.lang.Object;

import javax.net.ssl.HttpsURLConnection;

import static java.util.Objects.*;

public class UserManagerLibrary {

    //1. Funcio petita amb atribut corresponent
    //2. Definir petició amb constructora
    //3. Request amb id_peticio
    //4. Request real amb setRequestMethod

    private static final String USER_AGENT = "Mozilla/5.0";

    //private static final String user_id = "145";

    private static final String GET_URL = "https://pes-my-pet-care.herokuapp.com/singup?password=";

    /*private static final String username = "username=Pankaj";

    private static final String password = "password=123456";

    private static final String email = "email=pankaj@gmail.com";*/

    public static void PostUser(String username, String password, String email) throws IOException {
        URL obj = new URL(GET_URL+password);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setDoInput(true);
        con.setDoOutput(true);

        con.connect();
        //System.out.println(con.getResponseCode());

        /*con.setRequestProperty("username", username);
        con.setRequestProperty("password", password);
        con.setRequestProperty("email", email);*/
        //Map<String,String> UserParams= new HashMap<>();


        /*OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(postDataParams));

        writer.flush();
        writer.close();
        os.close();*/

            //Query amb tots els parametres de post de user
        /*Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("username", username)
                .appendQueryParameter("email", email);
        String query = builder.build().getEncodedQuery();*/

        StringBuilder tokenUri=new StringBuilder("username=");
        tokenUri.append(URLEncoder.encode(username,"UTF-8"));
        tokenUri.append("&email=");
        tokenUri.append(URLEncoder.encode(email,"UTF-8"));



        /*OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(tokenUri));

        writer.flush();
        writer.close();
        os.close();*/

            //Parametres nous
        /*OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
        //BufferedWriter writer = new BufferedWriter(
                //new OutputStreamWriter(os, StandardCharsets.UTF_8));
        os.write(String.valueOf(tokenUri));
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + obj);
        System.out.println("Post parameters : " + tokenUri);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());*/


        /*int responseCode = con.getResponseCode();
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
        }*/
    }

    /*private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder feedback = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                feedback.append("&");

            feedback.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            feedback.append("=");
            feedback.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return feedback.toString();
    }*/






}
