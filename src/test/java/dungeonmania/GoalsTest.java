package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import jdk.jfr.Timestamp;

public class GoalsTest {
    
    /**
     * Checks to see if goal is complete if player reaches 
     * exit
     */
    @Test
    public void testBasicExitGoal() {

    }

    /**
     * Check to see if goal is incomplete if player attempts to
     * do exit goal first before other goal in an AND goal
     */
    @Test 
    public void testAndGoalExitGoalFirst() {

    }

    /**
     * Check to see if goal is complete if player reaches exit first
     * before other goal in an OR goal
     */
    @Test 
    public void testOrGoalExitGoalFirst() {

    }
}
