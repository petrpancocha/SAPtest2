package com.petrpancocha.saptest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void printsPathsAndExpectedRestTimesForAllComplaints() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String expected = """
                All paths from Zone{id=1} to Zone{id=2} to fix an issue
                Path{from=Zone{id=1}, to=Zone{id=2}, roads=[Line{zoneA=Zone{id=1}, zoneB=Zone{id=2}, travelTime=5}]}
                Path{from=Zone{id=1}, to=Zone{id=2}, roads=[Line{zoneA=Zone{id=1}, zoneB=Zone{id=4}, travelTime=1}, Line{zoneA=Zone{id=3}, zoneB=Zone{id=4}, travelTime=20}, Line{zoneA=Zone{id=2}, zoneB=Zone{id=3}, travelTime=10}]}
                Minimum travel time (to + back) is 10
                Time to fix a complaint is 65
                Rest time is 55
                
                All paths from Zone{id=1} to Zone{id=3} to fix an issue
                Path{from=Zone{id=1}, to=Zone{id=3}, roads=[Line{zoneA=Zone{id=1}, zoneB=Zone{id=2}, travelTime=5}, Line{zoneA=Zone{id=2}, zoneB=Zone{id=3}, travelTime=10}]}
                Path{from=Zone{id=1}, to=Zone{id=3}, roads=[Line{zoneA=Zone{id=1}, zoneB=Zone{id=4}, travelTime=1}, Line{zoneA=Zone{id=3}, zoneB=Zone{id=4}, travelTime=20}]}
                Minimum travel time (to + back) is 30
                Time to fix a complaint is 15
                Rest time is 0
                
                """;

        assertEquals(expected.replace("\n", System.lineSeparator()), output.toString(StandardCharsets.UTF_8));
    }
}
