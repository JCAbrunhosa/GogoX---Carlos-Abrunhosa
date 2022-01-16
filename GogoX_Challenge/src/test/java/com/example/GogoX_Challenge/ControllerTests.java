package com.example.GogoX_Challenge;

import com.example.GogoX_Challenge.Controller.LotteryController;
import com.example.GogoX_Challenge.Objects.Participant;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ControllerTests {

    @Test
    public void shouldAddParticipant() throws SQLException {
        Participant participant = new Participant("aaa", "bbb", "99-00-22", 123123);
        LotteryController controller = new LotteryController();

        Assertions.assertEquals("The draw hasn't started yet. Wait a few seconds to enter the draw.", controller.addParticipant(participant));
    }

    @Test
    public void shouldReturnAllDrawsInAJSON() throws SQLException, JSONException {
        LotteryController controller = new LotteryController();
        System.out.println(controller.checkPreviousDraws().toString());
    }

    @Test
    public void shouldReturnAllWonDrawsInAJSON() throws SQLException, JSONException {
        Participant participant = new Participant("Zeca", "Abrunhosa", "42-26-63", 982390);
        LotteryController controller = new LotteryController();
        System.out.println(controller.checkWonDraws(participant).toString());
    }
}
