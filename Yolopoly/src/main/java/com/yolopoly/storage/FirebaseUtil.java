package com.yolopoly.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.yolopoly.managers.InGameManager;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseUtil {
    public FirebaseUtil() {
        try{
            FileInputStream serviceAccount =
                    new FileInputStream("com/yolopoly/data/key.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://yolopoly-120e5.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void getData(InGameManager innerEngine){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //TODO: ADD ROAD TO DATABASE VALUE IN PATH
        DatabaseReference ref = database.getReference("");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InGameManager post = dataSnapshot.getValue(InGameManager.class);
                //TODO: SET DATA COMING FROM POST
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void sendData(InGameManager innerEngine){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        //TODO: ADD HOSTER NICK COMING FROM MIDDLE ENGINE
        //TODO: SET CHILD TO COMING NICK
        //TODO: PUSH VALUES
    }

    //TODO: ADD A METHOD TO CONSTANTLY CHECK FOR UPDATES IN ENGINE

    //TODO: ADD A METHOD TO CREATE A ROOM

    //TODO: ADD A METHOD TO JOIN A ROOM

    //TODO: ADD A METHOD TO LEAVE A ROOM

    //TODO ADD A METHOD TO CLOSE A GAME

    //TODO: ADD A METHOD TO GET LOBBIES

    //TODO: ADD A METHOD TO JOIN LOBBIES
}