package com.example.GogoX_Challenge.Controller;

import com.example.GogoX_Challenge.Model.LotteryHandler;
import com.example.GogoX_Challenge.Objects.Participant;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class LotteryController {

    static LotteryHandler model = new LotteryHandler();

    @PostMapping(value = "/enter_draw", consumes = "application/json", produces = "application/json")
    public String addParticipant(@RequestBody Participant participant) throws SQLException {
        return model.addNewParticipant(participant);
    }

    @GetMapping(value = "/enter_draw/current")
    public String getCurrentDrawDetails(){
        return model.getCurrentDrawAndTimeLeft();
    }

    @GetMapping(value = "/check_draws", consumes = "application/json", produces = "application/json")
    public String checkPreviousDraws() throws SQLException, JSONException {
        return model.checkAllDraws().toString();
    }

    @GetMapping(value = "/won_draws", consumes = "application/json", produces = "application/json")
    public String checkWonDraws(@RequestBody Participant participant) throws SQLException, JSONException {
        return model.checkWonDraws(participant).toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enter_draw/results")
    @ResponseBody
    public String sendBackResult()  {
        return model.returnDrawWinner();
    }



}
