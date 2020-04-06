package org.pesmypetcare.pes_my_pet_care_apis;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.Date;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealManagerClient manager = new MealManagerClient();

        Date date = new Date(2017,3,4,13,50,12);
        MealData mealData = new MealData("Asparragus with sugar", 15.20);
        Meal meal = new Meal(date, mealData);

        manager.createMeal("john", "Laika", meal);
    }
}

