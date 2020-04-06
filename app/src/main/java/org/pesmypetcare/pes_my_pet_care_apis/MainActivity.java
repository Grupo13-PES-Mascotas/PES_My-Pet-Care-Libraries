package org.pesmypetcare.pes_my_pet_care_apis;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;


import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealManagerClient manager = new MealManagerClient();

        DateTime dateTime = null, dateTime1 = null, dateTime2 = null;
        try {
            dateTime = new DateTime(2017,7,5,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime1 = new DateTime(2017,3,5,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime2 = new DateTime(2017,7,5,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        MealData mealData = new MealData("Mis Asparagus", 55);
        Meal meal = new Meal(dateTime.toString(), mealData);


        // TESTS MEAL

        //manager.createMeal("john", "Laika", meal);
        //manager.deleteByDate("john", "Laika", dateTime);
        //manager.deleteAllMeals("john", "Laika");
        //manager.updateMealField("john", "Laika", dateTime,"kcal", 15.2);
        //manager.updateMealField("john", "Laika", dateTime,"mealName", "Merontokaocloeinog");

        /*
        try {
            System.out.println(manager.getMealData("john", "Laika", dateTime));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        /*

        try {
            System.out.println(manager.getAllMealData("john", "Memo"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            System.out.println(manager.getAllMealsBetween("john", "Laika", dateTime1, dateTime2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            System.out.println(manager.getMealData("john", "Laika", date));
            System.out.println(manager.getAllMealData("john", "Laika"));
            System.out.println(manager.getAllMealsBetween("john", "Laika", date1.toString(), date2.toString()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
    }
}

