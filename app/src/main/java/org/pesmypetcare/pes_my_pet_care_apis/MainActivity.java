package org.pesmypetcare.pes_my_pet_care_apis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PetManagerClient manager = new PetManagerClient();

        //pending issue: la washfreq se pone a 0...
        /*manager.createPet("toke", "santi", "Tristana", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);*/
        /*manager.createPet("token", "santi", "Laika", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);*/
        //Date myDate = new Date(2012, 03, 14);

        double kcal = 15.2;
        manager.updateField("token", "john", "Laika", PetManagerClient.RECOMMENDED_KCAL, kcal);
        /*manager.updateGender("john", "Tristan", "Male");
        manager.updateBreed("john", "Tristan", "Pastor Alemán");
        manager.updateWeight("john", "Tristan", 12.4);
        manager.updateBirthday("john", "Tristan", "2015-01-23");
        manager.updatePathologies("john", "Milu", "COVID-19");
        manager.updateRecKcal("john", "Milu", 120);
        manager.updateWashFreq("john", "Milu", 5);
        manager.deletePet("john", "Tristana");*/

        //manager.deletePet("token", "santi", "Tristana");
        //manager.deletePet("john", "Macarena");
        //manager.deletePet("john", "Milu");

        /*try {
            PetData data = manager.getPet("token","santi", "Tristana");
            System.out.println(data);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        /*try {
            List<Pet> pets = manager.getAllPets("token", "santi");
            System.out.println(pets);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        UserManagerClient client = new UserManagerClient();
        /*client.signUp("santi", "123456", "santi@gmail.com");
        System.out.println("Pasado signup");*/
        /*try {
            System.out.println("MAIN: " + client.getUser("kayle"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        /*client.deleteUser("kayle");
        client.updateEmail("alvaro", "nuevoEMail@mail.com");
        client.updatePassword("alvaro", "newpassword123456");*/
        String test = "Hello World";
        //client.saveProfileImage("myAccessToken", "santi", test.getBytes());
        /*try {
            byte[] result = client.downloadProfileImage("myAccessToken", "santi");
            System.out.println(new String(result));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        //manager.saveProfileImage("myAccessToken", "santi", "Tristana", test.getBytes());
        /*try {
            Map<String, byte[]> pets = manager.downloadAllProfileImages("token", "santi");
            System.out.println(new String(pets.get("Pepe")));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        /*try {
            byte[] pet = manager.downloadProfileImage("myAccessToken", "santi", "Tristana");
            System.out.println(new String(pet));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

