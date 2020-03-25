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

        //pending issue: la washfreq se pone a 0...
        /*manager.signUpPet("john", "Tristana", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);

*/
       /* manager.signUpPet("john", "Laika", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);
                */
        //Date myDate = new Date(2012, 03, 14);

        //manager.updateSex("john", "Tristan", "Male");
        //manager.updateRace("john", "Tristan", "Pastor Alemán");
        //manager.updateWeight("john", "Tristan", 12.4);
        //manager.updateBirthday("john", "Tristan", "2015-01-23");
        //manager.deletePet("john", "Tristana");
        //manager.updatePatologies("john", "Milu", "COVID-19");
        //manager.updateRecKcal("john", "Milu", 120);
        //manager.updateWashFreq("john", "Milu", 5);

        //manager.deletePet("john", "Excalibur");
        //manager.deletePet("john", "Macarena");
        //manager.deletePet("john", "Milu");


       try {
            //manager.getPet("john", "Tristana");
           manager.getAllPets("john");
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

