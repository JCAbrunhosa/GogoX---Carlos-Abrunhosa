package com.example.GogoX_Challenge.Model;

import com.example.GogoX_Challenge.Objects.Participant;
import org.json.JSONArray;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LotteryHandler {

    ConnectToDatabase connectToDatabase = new ConnectToDatabase();
    static ConcurrentHashMap<Integer, Participant> lotteryDraw = new ConcurrentHashMap<>();
    static ArrayList<Integer> participantsIds = new ArrayList<>();
    Timer drawTimer = new Timer();
    Timer inBetweenDrawsTimer = new Timer();
    static int drawNumber = 0;
    boolean hasGameStarted = false;
    int lotteryPrize = 1000;
    String winningKey;

    //Constructor
    public LotteryHandler(){
        System.out.println("The lottery will start shortly.");
        inBetweenDrawsTimer.schedule(new inBetweenDrawsTimer(), 10 * 1000);
    }

    //----------------------------Draw Related Functions------------------------//

    //Adds new participant. The new round only starts when the first user enter the draw.
    public String addNewParticipant(Participant participant) throws SQLException {

        if(!checkIfUUIDExists(participant.getUUID()))
            System.out.println(addUserToDB(participant));

        if(!hasGameStarted){
            return "The draw hasn't started yet. Wait a few seconds to enter the draw.";
        }else if(!participantsIds.contains(participant.getUUID())){
            winnerNotWinner(participant);
            participantsIds.add(participant.getUUID());
            System.out.println(participant.getLastName() + " has successfully entered the draw!");
            return "Successfully entered the draw!";
        }

        return "You have already participated on this draw. Wait for the next draw to participate again.";
    }

    //Returns the winner. Converts the hashset to an array, assigns a number to it, and returns the actual number.
    //Also adds the winner details to the Games database
    public String getDrawWinner() throws SQLException {

        if(lotteryDraw.size() >0){
            int i = 0;
            String toReturn = "";
            for (int uuid : lotteryDraw.keySet()) {
                Participant participant = lotteryDraw.get(uuid);
                connectToDatabase.addNewEntry(drawNumber,participant);
                i++;
            }

            if(i>1)
                toReturn = "Many participants have won draw number " + drawNumber + ", with a total prize of " + lotteryPrize/i + " HKD each.";
            else
                toReturn = "One participant has own draw number " + drawNumber + ", with a total prize of " + lotteryPrize + "HKD.";
            lotteryPrize=1000;
            return toReturn;
        }
        lotteryPrize += 1000;
        return "Now one has won the lottery prize this draw. Prize pool has been increased by 1000 HKD. Now total prize pool is " + lotteryPrize + ".";
    }

    //This method restarts the game as all its variables before accepting new ones.
    //Also registers when the game has been restarted
    public void restartDraw(){
        hasGameStarted=false;
        lotteryDraw = new ConcurrentHashMap<>();
        participantsIds = new ArrayList<>();
    }

    private void winnerNotWinner(Participant participant){
        if(participant.getKey().matches(winningKey))
            lotteryDraw.put(participant.getUUID(),participant);
    }

    //Method to check if any current draw is going on - Returns response and time left for the draw (if currently under way).
    public String getCurrentDrawAndTimeLeft(){

        if(hasGameStarted)
            return ("Draw number "+drawNumber+" is currently underway, with " + drawTimer.toString()+ " seconds left.");

        return "New draw will start very soon.";

    }

    //Timer for each draw. At the end, it restarts the game and gives the signal to return the winner.
    class DrawTimer extends TimerTask{

        @Override
        public void run() {

            try {
                System.out.println(getDrawWinner());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Next draw will start in 10 seconds.");
            inBetweenDrawsTimer.schedule(new inBetweenDrawsTimer(), 10 * 1000);
            hasGameStarted=false;
            restartDraw();
        }
    }

    //Timer for each draw. At the end, it restarts the game and gives the signal to return the winner.
    class inBetweenDrawsTimer extends TimerTask{

        @Override
        public void run() {
            drawTimer.schedule(new DrawTimer(), 20 * 1000);
            drawNumber++;
            hasGameStarted=true;
            System.out.println();
            System.out.println("Draw number " + drawNumber + " has started!");
            Random key = new Random();
            winningKey = (key.nextInt(99)) + "-" + key.nextInt(99) + "-" + key.nextInt(99);
            System.out.println("Winning key for this round: " + winningKey);

        }
    }


    //----------------------------Database Related Functions------------------------//

    //Method to check all draws until now
    //Returns a list of JSON objects
    public JSONArray checkAllDraws() throws SQLException, JSONException {
        return connectToDatabase.getAllDraws();
    }

    //Method to check if the user has won any draws
    //Returns a list of JSON Objects
    public JSONArray checkWonDraws(Participant participant) throws SQLException, JSONException {
        return connectToDatabase.getWonDraws(participant);
    }

    //Method to check if user already exists (this would be used for a possible login)
    public boolean checkIfUUIDExists(int uuid) throws SQLException {
        return connectToDatabase.checkIfUUIDExists(uuid);
    }

    //Method to add user to database(this would b e used for a possible login)
    private String addUserToDB(Participant participant) throws SQLException {
        return connectToDatabase.addUserToDB(participant);
    }
}
