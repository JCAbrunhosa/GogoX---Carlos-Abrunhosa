package com.example.GogoX_Challenge.Model;

import com.example.GogoX_Challenge.Objects.Participant;

import java.sql.*;
import org.json.*;

public class ConnectToDatabase {

    Connection con;

    public ConnectToDatabase(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Games","root","xxxxxx");
        }catch (Exception e){
            System.out.println("Error connecting to Database: " + e);
        }


    }

    public void addNewEntry(int gameNumber, Participant participant) throws SQLException {
        String query = " insert into Games (draw_id, participant_uuid, participant_firstName, participant_lastName, winningKey)"
                + " values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString (1, String.valueOf(gameNumber));
        preparedStmt.setString (2, String.valueOf(participant.getUUID()));
        preparedStmt.setString (3, participant.getFirstName());
        preparedStmt.setString(4, participant.getLastName());
        preparedStmt.setString(5, participant.getKey());

        preparedStmt.execute();
    }

    public JSONArray getAllDraws() throws SQLException, JSONException {


        JSONArray returnArray = new JSONArray();

        String query = "select * from Games";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            JSONObject participant = new JSONObject();
            int id = rs.getInt("draw_id");
            String winningKey = rs.getString("winningKey");
            String firstName = rs.getString("participant_firstName");
            String lastName = rs.getString("participant_lastName");
            participant.append("Game Number", id);
            participant.append("Winner's Key", winningKey);
            participant.append("Winner's First Name", firstName);
            participant.append("Winner's Last Name", lastName);
            returnArray.put(participant);
        }

        return returnArray;
    }

    public JSONArray getWonDraws(Participant participant) throws SQLException, JSONException {

        JSONArray returnArray = new JSONArray();

        PreparedStatement statement = con.prepareStatement("select * from Games where participant_uuid = ?");
        statement.setString(1, String.valueOf(participant.getUUID()));
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            JSONObject participantObject = new JSONObject();
            int id = rs.getInt("draw_id");
            participantObject.append(String.valueOf(id), participant.getKey());
            returnArray.put(participantObject);
        }

        return returnArray;
    }

    public String addUserToDB(Participant participant) throws SQLException {
        String query = " insert into Users (uuid, firstName, lastName)"
                + " values (?, ?, ?)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString (1, String.valueOf(participant.getUUID()));
        preparedStmt.setString (2, participant.getFirstName());
        preparedStmt.setString (3, participant.getLastName());
        preparedStmt.execute();

        return "User " + participant.getUUID() + " added to the database";
    }

    public boolean checkIfUUIDExists(int uuid) throws SQLException {

        PreparedStatement statement = con.prepareStatement("select * from Users where uuid = ? ");
        statement.setString(1, String.valueOf(uuid));
        ResultSet rs = statement.executeQuery();

        return rs.next();
    }


}
