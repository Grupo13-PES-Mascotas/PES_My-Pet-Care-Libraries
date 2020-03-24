package org.pesmypetcare.pes_my_pet_care_apis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.deleteUser;
import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.getUser;
import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.signUp;
import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.updateEmail;
import static org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary.updatePassword;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signUp("tehdgkfh", "sdthutth456456", "shjghttrsh@gmail.com");
        System.out.println("MAIN: " + getUser("kayle"));
        System.out.println("MAIN");
        //deleteUser("kayle");
        //updateEmail("alvaro", "nuevoEMail@mail.com");
        //updatePassword("alvaro", "newpassword123456");
    }
}
