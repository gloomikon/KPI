package com.company;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class MainTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Person.class).verify();
    }
}