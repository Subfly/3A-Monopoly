package com.yolopoly.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameTheme;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.managers.LobbyManager;
import com.yolopoly.managers.MainMenuManager;
import com.yolopoly.models.bases.GameListData;
import com.yolopoly.models.bases.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseUtil {
    private static FirebaseUtil util = null;
    public static ArrayList<GameListData> gameListData = new ArrayList<>();
    private LobbyManager lobbyManager;
    private InGameManager inGameManager;
    private MainMenuManager mainMenuManager;

    public static synchronized FirebaseUtil getInstance(){
        if(util == null){
            util = new FirebaseUtil();
        }
        return util;
    }

    private FirebaseUtil() {
        try{
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/META-INF/key.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://yolopoly-120e5.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            mainMenuManager = MainMenuManager.getInstance();
            lobbyManager = LobbyManager.getInstance();
            inGameManager = InGameManager.getInstance();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void initGameList(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        refGameList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameListData.clear();
                for(DataSnapshot ds1 : dataSnapshot.getChildren()){
                    GameListData data = ds1.getValue(GameListData.class);
                    gameListData.add(data);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    //TODO: FIRST STEP FOR CREATE A GAME
    public void createRoom(String hosterNick){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGameList = database.getReference("gameList");
        DatabaseReference refMiddle = database.getReference("middle");
        lobbyManager = LobbyManager.getInstance();
        lobbyManager.setOnline(true);
        lobbyManager.addBot();
        lobbyManager.getPlayerArrayList().get(lobbyManager.getPlayerCount()-1).setHuman(true);
        lobbyManager.getPlayerArrayList().get(lobbyManager.getPlayerCount()-1).setName(hosterNick);
        lobbyManager.setAdmin(lobbyManager.getPlayerArrayList().get(lobbyManager.getPlayerCount()-1));
        playerCount++;
        //Create game
        GameListData data = new GameListData(hosterNick, GameMode.vanilla, GameTheme.vanilla, 0, "");
        //Send data
        refGameList.child(hosterNick).setValue(data, (databaseError, databaseReference) -> {
            System.out.println("Game created successfully for player:" + hosterNick + "!");
            refGameList.child(hosterNick).child("playerCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    refGameList.child(hosterNick).child("playerCount").setValue(playerCount,(databaseError, databaseReference) ->{
                        System.out.println("playerCount ++");
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });

        refMiddle.child(hosterNick).setValue(lobbyManager, (err, ref)->{
            System.out.println("Engine initialized on server successfully!");
        });
    }

    static int playerCount = 0;
    private int playersIndex = 0;

    //TODO: SECOND STEP FOR CREATE A GAME
    public void joinLobby(int selectedIndex){
        String hosterNick = gameListData.get(selectedIndex).getAdmin();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("middle").child(hosterNick).child("playerCount");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerCount = dataSnapshot.getValue(Integer.class);
                playerCount++;
                reference.setValue(playerCount, (databaseError, databaseReference) -> {
                            System.out.println("count++");
                        }
                );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        playersIndex = playerCount;

        System.out.println(playersIndex);
        DatabaseReference refMiddle = database.getReference("middle");
        refMiddle.child(hosterNick).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LobbyManager middleEngine = dataSnapshot.getValue(LobbyManager.class);
                lobbyManager.setAdmin(middleEngine.getPlayerArrayList().get(0));
                lobbyManager.setGameMode(middleEngine.getGameMode());
                lobbyManager.setGameTheme(middleEngine.getGameTheme());
                lobbyManager.setMaxPlayerCount(middleEngine.getMaxPlayerCount());
                lobbyManager.setIsAllReady(middleEngine.getIsAllReady());
                lobbyManager.setPawns(middleEngine.getPawns());
                //TODO: HANDLE PAWN
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error happened on setMiddleEngine()");
            }
        });
    }

    //TODO: REST HERE IS INTERNAL STEPS OF CREATE A GAME
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