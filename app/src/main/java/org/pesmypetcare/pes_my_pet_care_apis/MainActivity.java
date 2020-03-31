package org.pesmypetcare.pes_my_pet_care_apis;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PetManagerClient manager = new PetManagerClient();

        //pending issue: la washfreq se pone a 0...
        /*manager.signUpPet("john", "Tristana", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);

        manager.signUpPet("john", "Laika", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);*/
        //Date myDate = new Date(2012, 03, 14);

        /*manager.updateGender("john", "Tristan", "Male");
        manager.updateBreed("john", "Tristan", "Pastor Alemán");
        manager.updateWeight("john", "Tristan", 12.4);
        manager.updateBirthday("john", "Tristan", "2015-01-23");
        manager.updatePathologies("john", "Milu", "COVID-19");
        manager.updateRecKcal("john", "Milu", 120);
        manager.updateWashFreq("john", "Milu", 5);
        manager.deletePet("john", "Tristana");*/

        //manager.deletePet("john", "Excalibur");
        //manager.deletePet("john", "Macarena");
        //manager.deletePet("john", "Milu");

        /*try {
            manager.getPet("john", "Tristana");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        /*try {
            List<Pet> pets = manager.getAllPets("Albert");
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
        /*client.saveProfileImage("myAccessToken", "user", test.getBytes());*/
        /*try {
            byte[] result = client.downloadProfileImage("myAccessToken", "user");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        //manager.saveProfileImage("myAccessToken", "user", "Linux", test.getBytes());
        try {
            manager.downloadProfileImage("myAccessToken", "user", "Linux");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

