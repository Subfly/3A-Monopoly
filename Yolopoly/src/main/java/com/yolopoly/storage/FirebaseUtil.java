package com.yolopoly.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.managers.LobbyManager;

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

    public void getData(InGameManager innerEngine, String hosterNick){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("rooms");

        ref.child(hosterNick).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InGameManager post = dataSnapshot.getValue(InGameManager.class);
                innerEngine.setAuctionPropertyIndex(post.getAuctionPropertyIndex());
                innerEngine.setBank(post.getBank());
                innerEngine.setBoard(post.getBoard());
                innerEngine.setBrokenPlayersMoneyHash(post.getBrokenPlayersMoneyHash());
                innerEngine.setCurrentBid(post.getCurrentBid());
                innerEngine.setChat(post.getChat());
                innerEngine.setCurrentPlayerAuctioning(post.getCurrentPlayerAuctioning());
                innerEngine.setCurrentPlayerId(post.getCurrentPlayerId());
                innerEngine.setDice(post.getDice());
                innerEngine.setLog(post.getLog());
                innerEngine.setParticipants(post.getParticipants());
                innerEngine.setState(post.getState());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void sendData(InGameManager innerEngine, String hosterNick){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        ref.child("rooms").child(hosterNick).setValue(innerEngine, (databaseError, databaseReference) -> {
            System.out.println("Data sent to server!\n\nDEBUG\nhosterNick: " + hosterNick + "\nlast player id: " + innerEngine.getCurrentPlayerId() + "\n");
        });
    }

    //TODO: ADD A METHOD TO CONSTANTLY CHECK FOR UPDATES IN ENGINE

    //TODO: ADD A METHOD TO CREATE A ROOM
    public void createRoom(LobbyManager middleEngine, String hosterNick){

    }

    //TODO: ADD A METHOD TO JOIN A ROOM
    public void join(String hosterNick, String nickName){

    }

    //TODO: ADD A METHOD TO LEAVE A ROOM


    //TODO ADD A METHOD TO CLOSE A GAME

    //TODO: ADD A METHOD TO GET LOBBIES

    //TODO: ADD A METHOD TO JOIN LOBBIES
}