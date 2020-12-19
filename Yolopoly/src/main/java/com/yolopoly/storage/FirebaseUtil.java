package com.yolopoly.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameTheme;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.models.bases.GameListData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseUtil {
    public FirebaseUtil() {
        try{
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/META-INF/key.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://yolopoly-120e5.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void createRoom(String hosterNick){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        //Create game hash
        GameListData data = new GameListData(hosterNick, GameMode.vanilla, GameTheme.vanilla, 0, "");

        //Send data
        refGameList.child(hosterNick).setValue(data, (databaseError, databaseReference) -> {
            System.out.println("Game created successfully for player:" + hosterNick + "!");
        });
    }

    public void setPassword(String hosterNick, String password){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        refGameList.child(hosterNick).child("password").setValue(password, (databaseError, databaseReference) -> {
            System.out.println("Password changed successfully!");
        });
    }

    public void setPlayerCount(String hosterNick, int playerCount){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        refGameList.child(hosterNick).child("playerCount").setValue(playerCount, (databaseError, databaseReference) -> {
            System.out.println("Player count changed to: " + playerCount);
        });
    }

    public void setGameMode(String hosterNick, GameMode mode){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        refGameList.child(hosterNick).child("mode").setValue(mode, (databaseError, databaseReference) -> {
            System.out.println("Mode changed to: " + mode);
        });
    }

    public void setGameTheme(String hosterNick, GameTheme theme){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        refGameList.child(hosterNick).child("theme").setValue(theme, (databaseError, databaseReference) -> {
            System.out.println("Theme changed to: " + theme);
        });
    }

    public ArrayList<GameListData> getGameList(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");

        ArrayList<GameListData> dataList = new ArrayList<>();

        refGameList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds1 : dataSnapshot.getChildren()){
                    System.out.println(ds1);
                    GameListData data = ds1.getValue(GameListData.class);
                    System.out.println(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
        return dataList;
    }

    public void getData(InGameManager innerEngine, String hosterNick){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("rooms");

        ref.child(hosterNick).child("inner").addValueEventListener(new ValueEventListener() {
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
        DatabaseReference ref = database.getReference("rooms");

        ref.child(hosterNick).child("inner").setValue(innerEngine, (databaseError, databaseReference) -> {
            System.out.println("Data sent to server!\n\nDEBUG\nhosterNick: " + hosterNick + "\nlast player id: " + innerEngine.getCurrentPlayerId() + "\n");
        });
    }

    //TODO: ADD A METHOD TO CONSTANTLY CHECK FOR UPDATES IN ENGINE

    public void join(String hosterNick, String nickName){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(hosterNick).child("middle");
        //TODO CONTINUE METHOD FROM HERE
    }

    //TODO: ADD A METHOD TO LEAVE A ROOM
    public void leaveLobby(String hosterNick, String nickName){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.child(hosterNick).child("middle");
        //TODO CONTINUE METHOD FROM HERE
    }

    //TODO ADD A METHOD TO LEAVE A GAME
    public void leaveGame(String hosterNick, String nickName){

    }

    //TODO: ADD A METHOD TO GET LOBBIES
    public HashMap<String, HashMap<String, String>> getLobbies(){
        HashMap<String, HashMap<String, String>> returningHash = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rooms");
        ref.orderByKey().limitToFirst(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> dataHash = new HashMap<>();
                // TODO: ADD READING FROM DATA
                // returningHash.put(dataSnapshot.getKey(), )
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return returningHash;
    }
}