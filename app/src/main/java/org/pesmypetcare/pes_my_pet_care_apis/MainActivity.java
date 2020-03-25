package org.pesmypetcare.pes_my_pet_care_apis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.pesmypetcare.usermanagerlib.UserData;
import org.pesmypetcare.usermanagerlib.UserManagerClient;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signUp("tehdgkfh", "sdthutth456456", "shjghttrsh@gmail.com");
        UserManagerClient client = new UserManagerClient();
        try {
            UserData user = client.getUser("usuario_de_pruebas");
            System.out.println("Usuario: " + user);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MAIN");
        //deleteUser("kayle");
        //updateEmail("alvaro", "nuevoEMail@mail.com");
        //updatePassword("alvaro", "newpassword123456");
    }
}
