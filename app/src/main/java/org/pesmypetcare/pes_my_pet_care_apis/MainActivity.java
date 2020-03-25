package org.pesmypetcare.pes_my_pet_care_apis;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.PetManagerLibrary;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PetManagerLibrary manager = new PetManagerLibrary();
        //manager.signUpPet("john", "Tristan", "Female", "Husky Siberiano", "2016-03-30", 13.4);
        //Date myDate = new Date(2012, 03, 14);


        //manager.updateSex("john", "Tristan", "Male");
        //manager.updateRace("john", "Tristan", "Pastor Alemán");
        //
        //manager.updateWeight("john", "Tristan", 12.4);
        //manager.updateBirthday("john", "Tristan", "2015-01-23");
        //manager.deletePet("john", "Tristan");

        try {
            manager.getPet("john", "Excalibur");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //signUp("tehdgkfh", "sdthutth456456", "shjghttrsh@gmail.com");
        /*try {
            System.out.println("MAIN: " + getUser("kayle"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MAIN");
        //deleteUser("kayle");
        //updateEmail("alvaro", "nuevoEMail@mail.com");
        //updatePassword("alvaro", "newpassword123456");*/
    }
}

