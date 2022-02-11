package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DomainTest {

    @Test
    void add() {
        assertEquals(2, new Domain().add(1, 1));
    }
}