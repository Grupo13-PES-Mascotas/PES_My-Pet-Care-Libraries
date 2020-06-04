package org.pesmypetcare.pes_my_pet_care_apis;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.communitymanager.datacontainers.Message;
import org.pesmypetcare.communitymanager.managers.ForumManagerClient;
import org.pesmypetcare.communitymanager.managers.ForumManagerClient;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.usermanager.clients.pet.PetManagerClient;
import org.pesmypetcare.usermanager.clients.user.UserManagerClient;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        /*PetManagerClient client = new PetManagerClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Weight weight = new Weight("2020-05-08T00:00:00", 2.3);
                try {
                    client.addFieldCollectionElement("token", "Albert Pinto Gil", "Estela", PetData.WEIGHTS,
                            weight.getKey(), weight.getBodyAsMap());
                } catch (MyPetCareException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();*/


        // TESTS USER


        UserManagerClient client = new UserManagerClient();
        Thread thread = new Thread(() -> {
            try {
                //byte[] img = client.downloadProfileImage("token", "Albert Pinto Gil");
                //System.out.println(img.length);
                System.out.println(client.getUser("token", "QABtiSlbB6NkXFCeDa4abRGSopT2"));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        /*try {
            if (!client.usernameAlreadyExists("santi")) {
                client.createUser("iw2VHtSHeoZohD3dAWRafXnb5x42", user);
            }
            //client.updateField("token", "santi", UserManagerClient.EMAIL, "mynewEmail@mail.com");
            //client.updateField("token", "santi", UserManagerClient.PASSWORD, "safawr32efwrw");
            client.updateField("token", "santi", UserManagerClient.USERNAME, "Santiago");
            //client.deleteUserFromDatabase("token", "8jzc2Kbz46PWdIb2UMavsLO02UF3");
            //client.deleteUser("token", "8jzc2Kbz46PWdIb2UMavsLO02UF3");
            //System.out.println(client.getUser("token", "santi"));
            //client.getUser("token", "santi");
            //int code = client.signUp("Caudillo", "11231231", "caudillo@email.com");
            //int code = client.deleteUserFromDatabase("token", "Caudillo");
            System.out.println(code);
            text.setText(String.valueOf(code));
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }*/
        /*PetManagerClient client = new PetManagerClient();
        Thread thread = new Thread(() -> {
            try {
                client.createPetSync("token", "santi",
                        new Pet("Max", new PetData(GenderType.Male, "Huskie", "2020-04-09T20:34:00", null, null,
                        null)));
            } catch (MyPetCareException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Pasado");
        });
        thread.start();*/
        //System.out.println(client.createUser("iw2VHtSHeoZohD3dAWRafXnb5x42", user));
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

        //TEST GROUPS
        /*Thread thread = new Thread(() -> {
            GroupManagerClient groupManager = new GroupManagerClient();
            List<String> tags = new ArrayList<>();
            tags.add("empo");
            tags.add("empotrador");
            String groupName = "Prueba 20";
            GroupData data = new GroupData(groupName, "santi",null, null);
            try {
                //groupManager.createGroup(data);
                //groupManager.deleteGroup(groupName);
                //groupManager.createGroup(data);
                Map<String, String> dog_people = groupManager.getGroup("Bienvenidos a My Pet Care").getMembers();
                System.out.println("Print 1: " + dog_people);
                //System.out.println("Print 2: " + groupManager.getAllGroups());
                //System.out.println("Print 3: " + groupManager.getAllTags());
                //System.out.println("Print 4: " + groupManager.getUserSubscriptions("token", "santi"));
                //groupManager.updateField(groupName, "description", "Hola gente estoy doraimio");
                //groupManager.updateField(groupName, "name", "Probando Cosas");
                //tags.remove("empo");
                //List<String> tags2 = new ArrayList<>();
                //tags2.add("doraimio");
                //groupManager.updateGroupTags("Probando Cosas", tags, tags2);
                //groupManager.subscribe("token", "Probando Cosas", "Enric Hernando");
                //System.out.println("Print 5: " + groupManager.getUserSubscriptions("token", "Enric Hernando"));
                //groupManager.unsubscribe("token", "Probando Cosas", "Enric Hernando");
                //System.out.println("Print 6: " + groupManager.getUserSubscriptions("token", "Enric Hernando"));
                //System.out.println("Print 7: " + groupManager.getAllGroups());
                //System.out.println("Print 8: " + groupManager.getAllTags());
                //groupManager.deleteGroup("Probando Cosas");
                //groupManager.updateGroupIcon("token", "Ansiano", "hola".getBytes());
                //byte[] img = groupManager.getGroupIcon("Ansiano");
                //System.out.println(new String(img));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            System.out.println("ACABADO");
        });
        thread.start();
        //TEST FORUMS
        /*Thread thread = new Thread(() -> {
            ForumManagerClient forumManager = new ForumManagerClient();
            List<String> tags = new ArrayList<>();
            tags.add("PES");
            String groupName1 = "Ansiano";
            String groupName2 = "Prueba 1";
            String forumName = "Vamos que nos vamos";
            String forumName2 = "Peluquerias";
            ForumData forum = new ForumData(forumName, "santi", new ArrayList<>());
            ForumData forum2 = new ForumData("Marc me aburro", "santi", tags);
            //Message message = new Message("santi", "Hola");
            try {
                //forumManager.createForum("Dog people", forum);
                //forumManager.deleteForum(groupName2, forumName);
                //forumManager.createForum(groupName1, forum2);
                //System.out.println(forumManager.getForum(groupName1, forumName));
                //System.out.println(forumManager.getAllForums(groupName1));
                //forumManager.updateName(groupName1, forumName, forumName2);
                //forumManager.updateTags(groupName1, forumName2, tags, null);
                //forumManager.postMessage("token", groupName1, forumName2, message);
                //System.out.println(forumManager.getAllPostsImagesFromForum("token", groupName1, forumName2));
                //forumManager.deleteMessage("token", groupName1, forumName2, "santi", "2020-04-23T23:40:09");
                //forumManager.likeMessage("token", "santi", groupName1, forumName2, "Albert Pinto Gil",
                //        "2020-04-24T08:22:12", true);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            System.out.println("FIN");
        });
        thread.start();*/

        // TEST REPORT i UNBAN
        /*
        Thread thread = new Thread(() -> {
            ForumManagerClient forumManager = new ForumManagerClient();
            List<String> tags = new ArrayList<>();
            String token = "token";
            String groupName = "Dog people";
            String forumName = "Huskies Lovers";
            String creator = "Marc Simó Guzmán";
            String reporter = "Kilom";
            String date = "2020-06-03T11:38:00";
            try {
               //forumManager.reportMessage(token, groupName, forumName, creator, reporter, date);
                forumManager.unbanMessage(token, groupName, forumName, creator, date);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            System.out.println("FIN");
        });
        thread.start();*/
    }
}

