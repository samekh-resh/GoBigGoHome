package com.games.gobigorgohome;

import junit.framework.TestCase;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class NPCTest {
    NPC manager;
//    we'll need to do testing on the exceptions, for either exception
    @Before
    public void setUp() throws IOException, ParseException {
        manager = new NPC("maintenance guy");

    }

//one test for true when the name is right and one test for false
    @Test
    public void getNPCName_shouldReturnStringOfName_whenCalled() {
        assertEquals(manager.getNpcName(), "Kate the maintenance lady");
    }
    @Test
    public void getNPCLocation_shouldReturnStringLocation_whenCalled() {
        assertEquals(manager.getNpcLocation(), "front desk");
    }
    @Test
    public void getInventory_shouldReturnInventory_whenCalled() {
        assertEquals(manager.getInventory().get(0), "wrench");
    }

    @Test
    public void getPhrases_shouldReturnPhrase_whenCalled() {
        assertEquals(manager.getPhrases().get(0), "Hey what's going on dude? You need this key?");
    }



}