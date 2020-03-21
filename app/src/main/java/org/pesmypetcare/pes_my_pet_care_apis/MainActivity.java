package org.pesmypetcare.pes_my_pet_care_apis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary.UserManagerLibrary;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            UserManagerLibrary.PostUser("kayle", "123abc", "kayle@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
