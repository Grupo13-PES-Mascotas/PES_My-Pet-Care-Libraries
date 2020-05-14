package org.pesmypetcare.pes_my_pet_care_apis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.pesmypetcare.communitymanager.ChatException;
import org.pesmypetcare.communitymanager.ChatModel;

public class ForumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        final ChatModel viewModel = new ViewModelProvider(this).get(ChatModel.class);
        viewModel.getMessage().observe(this, System.out::println);
        try {
            viewModel.doAction("Ansiano", "Peluquerias");
        } catch (ChatException e) {
            e.printStackTrace();
        }
    }
}
