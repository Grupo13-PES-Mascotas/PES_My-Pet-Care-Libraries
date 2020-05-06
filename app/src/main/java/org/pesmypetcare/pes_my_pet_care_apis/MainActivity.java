package org.pesmypetcare.pes_my_pet_care_apis;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Santiago Del Rey
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.Hello_text);

        // TESTS PET
        /*
        PetManagerClient manager = new PetManagerClient();

        DateTime dateTime1 = null, dateTime2 = null, dateTime3 = null, dateTime4 = null;

        try {
            dateTime1 = DateTime.Builder.build(1996,4,24,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        try {
            dateTime2 = DateTime.Builder.build(1997,4,24,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime3 = DateTime.Builder.build(1998,4,24,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        try {
            dateTime4 = DateTime.Builder.build(1999,4,24,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        PetData petData = new PetData(GenderType.Female, "Huskie", dateTime1.toString(), "None", "Constant attention"
            , 84.0);
        Pet pet = new Pet("Santiago", petData);
/*
        // Pet creation
        try {
            manager.createPet("token", "Manolo", pet);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 */
        // Pet simple functions
  /*
        try {
            System.out.println("Start:");
            System.out.println(manager.getPet("token", "Manolo", "Santiago"));
            System.out.println(manager.getAllPets("token", "Manolo"));
            //manager.deletePet("token", "Manolo", "Mentolada");
            System.out.println(manager.getSimpleField("token", "Manolo", "Santiago",
                Pet.BIRTH));
            System.out.println(manager.updateSimpleField("token", "Manolo", "Santiago",
                Pet.BIRTH, dateTime3.toString()));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
  /*

        Map<String, Object> body = new HashMap<>();
        body.put("kcal", 32.2);
        body.put("mealName", "I can't remember it");
        // Pet collection functions
        try {
            //System.out.println(manager.addFieldCollectionElement("token", "Manolo", "Santiago", PetData.MEALS,
            // dateTime4.toString(), body));
            //System.out.println(manager.deleteFieldCollectionElement("token", "Manolo",
            //    "Santiago", PetData.MEALS, dateTime4.toString()));
            //System.out.println(manager.updateFieldCollectionElement("token", "Manolo",
            //    "Santiago", PetData.MEALS, dateTime2.toString(), body));
            System.out.println(manager.getFieldCollectionElement("token", "Manolo",
                    "Santiago", PetData.MEALS, dateTime2.toString()));
            System.out.println(manager.getFieldCollection("token", "Manolo",
                "Santiago", PetData.MEALS));
            System.out.println(manager.getFieldCollectionElementsBetweenKeys("token", "Manolo",
                "Santiago", PetData.MEALS, dateTime1.toString(), dateTime2.toString()));
            //System.out.println(manager.deleteFieldCollection("token", "Manolo",
            //    "Santiago", PetData.MEALS));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        // TESTS GOOGLE CALENDAR
/*
        GoogleCalendarManagerClient manager = new GoogleCalendarManagerClient();

        DateTime dateTime = null, dateTime1 = null;
        try {
            dateTime = DateTime.Builder.build(2020,4,24,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime1 = DateTime.Builder.build(2020,4,24,17,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        EventData eventData = new EventData("eventid", "My Second Event",
            "A really pretty Location", "Nice event", EventData.BASIL, 50,
            4, dateTime.toString(), dateTime1.toString());


        try {
            System.out.println(manager.getAllEventsFromCalendar("ya29.a0Ae4lvC03arvXYkQsKjUuSwa-V4H1T4Ixcl2MG-duXJaN" +
                    "G12l6jpcpNjEPBs2tH1QsZhOII_x9cC2U5j1yHhIsEsUoM3cd36r9aizqB1W9mOC-iPWwUiwdqG7uYVxdcVN1SgU0eFpOP" +
                    "MNJfsNzDZXWeR3EECadDJbsUM",
                "john", "Laika"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


//TEST MEDICATION//
        /*
        MedicationManagerClient manager = new MedicationManagerClient();

        DateTime dateTime = null, dateTime1 = null, dateTime2 = null;
        try {
            dateTime = DateTime.Builder.buildFullString(2017,1,5,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime1 = new DateTime(2016,12,25,13,50,12);
          
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        try {
            dateTime2 = new DateTime(2017,4,4,13,50,12);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        String medName = "Espedifen";
        String medName2 = "Antizin";
        MedicationData medData = new MedicationData(6, 2,1);
        Medication med = new Medication(dateTime.toString(), medName, medData);
        MedicationData medData2 = new MedicationData(1, 1,5);
        Medication med2 = new Medication(dateTime2.toString(), medName2, medData2);

        /*
        try {
            manager.createMedication("accessToken","john", "Laika", med);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            manager.deleteByDateName("token", "john", "Laika", dateTime, medName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        */

        /*
        try {
            manager.getMedicationData("accessToken","john", "Laika", dateTime, medName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        */
/*
        try {
            manager.createMedication("accessToken","john", "Laika", med2);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

 */
/*
        try {
            manager.getAllMedicationData("accessToken","john", "Laika");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
 */
/*
        try {
            manager.getAllMedicationsBetween("accessToken","john", "Laika", dateTime1, dateTime2);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
/*
        try {
            manager.updateMedicationField("accessToken","john", "Laika", dateTime, medName,
                    "quantity", 5.0);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
 */

/*
        try {
            manager.deleteAllMedications("accessToken", "john", "Laika");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
*/


        // TESTS MEAL
        /*
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

        try {
            manager.updateMealField("accessToken","john", "Laika", dateTime,
                MealManagerClient.MEALNAME, "Malacoton");
            System.out.println("Update done");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        // TESTS meal

        //manager.createMeal("john", "Laika", meal);
        //manager.deleteByDate("john", "Laika", dateTime);
        //manager.deleteAllMeals("john", "Laika");
        //manager.updateMealField("john", "Laika", dateTime,"kcal", 15.2);
        //manager.updateMealField("john", "Laika", dateTime,"mealName", "Merontokaocloeinog");
        /*manager.createPet("toke", "santi", "Tristana", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);*/
        /*manager.createPet("token", "santi", "Laika", "Female", "Husky Siberiano",
                "2016-03-30", 13.4, "Coronavirus",
                150, 2);*/
        //Date myDate = new Date(2012, 03, 14);

        /*double kcal = 15.2;
        try {
            int i = manager.updateField("token", "john", "Laika", PetManagerClient.RECOMMENDED_KCAL, kcal);
            System.out.println(i);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        /*manager.updateGender("john", "Tristan", "Male");
        manager.updateBreed("john", "Tristan", "Pastor Alemán");
        manager.updateWeight("john", "Tristan", 12.4);
        manager.updateBirthday("john", "Tristan", "2015-01-23");
        manager.updatePathologies("john", "Milu", "COVID-19");
        manager.updateRecKcal("john", "Milu", 120);
        manager.updateWashFreq("john", "Milu", 5);
        manager.deletePet("john", "Tristana");*/

        //manager.deletePet("token", "santi", "Tristana");
        //manager.deletePet("john", "Macarena");
        //manager.deletePet("john", "Milu");

        /*try {
            PetData data = manager.getPet("token","santi", "Tristana");
            System.out.println(data);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        /*
        try {
            System.out.println(manager.getMealData("john", "Laika", dateTime));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<Pet> pets = manager.getAllPets("token", "alvaro");
            System.out.println(pets);
        } catch (ExecutionException | InterruptedException e) {
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


        // TESTS USER


/*

        UserManagerClient client = new UserManagerClient();
        UserData user = new UserData("santi", "santi@mail.com", "123455678");
        try {
            if (!client.usernameAlreadyExists("santi")) {
                client.createUser("iw2VHtSHeoZohD3dAWRafXnb5x42", user);
            }
            //client.updateField("token", "santi", UserManagerClient.EMAIL, "mynewEmail@mail.com");
            //client.updateField("token", "santi", UserManagerClient.PASSWORD, "safawr32efwrw");
            //client.updateField("token", "santi", UserManagerClient.USERNAME, "santi2");
            //client.deleteUserFromDatabase("token", "8jzc2Kbz46PWdIb2UMavsLO02UF3");
            //client.deleteUser("token", "8jzc2Kbz46PWdIb2UMavsLO02UF3");
            //System.out.println(client.getUser("token", "santi"));
            //client.getUser("token", "santi");
            //int code = client.signUp("Caudillo", "11231231", "caudillo@email.com");
            //int code = client.deleteUserFromDatabase("token", "Caudillo");
            /*System.out.println(code);
            text.setText(String.valueOf(code));*/
        //} catch (ExecutionException | InterruptedException | JSONException e) {
         //   e.printStackTrace();
        //}
        /*client.signUp("santi", "123456", "santi@gmail.com");
        System.out.println("Pasado signup");*/
        /*try {
            System.out.println("MAIN: " + client.getUser("kayle"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        /*try {
            client.updateField("token", "john", UserManagerClient.EMAIL, "email@mail.com");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        //client.deleteUser("kayle");
        //client.updateEmail("token", "alvaro", "nuevoEMail@mail.com");
        //client.updatePassword("alvaro", "newpassword123456");
        //String test = "Hello World";
        //client.saveProfileImage("myAccessToken", "santi", test.getBytes());
        /*try {
            byte[] result = client.downloadProfileImage("myAccessToken", "santi");
            System.out.println(new String(result));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        //manager.saveProfileImage("myAccessToken", "santi", "Tristana", test.getBytes());
        /*try {
            Map<String, byte[]> pets = manager.downloadAllProfileImages("token", "santi");
            System.out.println(new String(pets.get("Pepe")));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        /*try {
            byte[] pet = manager.downloadProfileImage("myAccessToken", "santi", "Tristana");
            System.out.println(new String(pet));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

