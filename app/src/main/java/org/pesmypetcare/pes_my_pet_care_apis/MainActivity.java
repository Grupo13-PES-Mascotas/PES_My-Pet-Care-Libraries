package org.pesmypetcare.pes_my_pet_care_apis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.ExecutionException;

import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signUp("tehdgkfh", "sdthutth456456", "shjghttrsh@gmail.com");
        try {
            System.out.println("MAIN: " + getUser("kayle"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MAIN");
        //deleteUser("kayle");
        //updateEmail("alvaro", "nuevoEMail@mail.com");
        //updatePassword("alvaro", "newpassword123456");
    }
}
