package com.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test void pageContainsMessage() {
        assertTrue(App.buildPage("Hello").contains("<h1>Hello</h1>"));
    }
    @Test void defaultMessageIsSet() {
        assertEquals("Hello, from gcp 2", App.MESSAGE);
    }
    @Test void healthReportsUp() {
        assertEquals("{\"status\":\"UP\"}", App.health());
    }
}
