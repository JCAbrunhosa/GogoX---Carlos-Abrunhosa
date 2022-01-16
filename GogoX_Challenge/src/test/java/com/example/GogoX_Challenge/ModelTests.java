package com.example.GogoX_Challenge;

import com.example.GogoX_Challenge.Model.LotteryHandler;
import com.example.GogoX_Challenge.Objects.Participant;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ModelTests {

    @Test
    public void userIsAddedThenReturnString() throws SQLException {

        Participant participant = new Participant("aaa", "bbb", "99-00-22", 123123);
        LotteryHandler model = new LotteryHandler();

        System.out.println(model.addNewParticipant(participant));
    }

    @Test
    public void getsDrawWinnerAndPrintsResult() throws SQLException {
        Participant participant = new Participant("aaa", "bbb", "99-00-22", 982390);
        LotteryHandler model = new LotteryHandler();
        model.addNewParticipant(participant);
        System.out.println(model.getDrawWinner());
    }

    @Test
    public void shouldGetDrawsAndPrintsThem() throws SQLException, JSONException {
        LotteryHandler model = new LotteryHandler();
        System.out.println(model.checkAllDraws());
    }

    @Test
    public void shouldGetOwnDrawsAndPrintsThem() throws SQLException, JSONException {
        Participant participant = new Participant("aaa", "bbb", "42-26-63", 982390);
        LotteryHandler model = new LotteryHandler();
        System.out.println(model.checkWonDraws(participant));

    }

    @Test
    public void addsUserToDBAndPrintsResult() throws SQLException {
        Participant participant = new Participant("aaa", "bbb", "42-26-63", 123123);
        LotteryHandler model = new LotteryHandler();
        System.out.println(model.addNewParticipant(participant));
    }

    @Test
    public void checksIfUUIDExistsAndPrintsResult() throws SQLException {
        Participant participant = new Participant("aaa", "bbb", "99-00-22", 123123);
        LotteryHandler model = new LotteryHandler();
        Assertions.assertTrue(model.checkIfUUIDExists(participant.getUUID()));
    }
}
