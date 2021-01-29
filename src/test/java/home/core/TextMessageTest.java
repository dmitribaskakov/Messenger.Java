package home.core;

//import java.util.HashMap;
//import java.util.Map;

import static org.junit.Assert.*;

public class TextMessageTest {

//    static final Map<String, String> testData = new HashMap<>();

    @org.junit.Before
    public void setUp() {
//        testData.put("123", "123");
    }

    @org.junit.Test
    public void testEquals() {
        TextMessage actual = new TextMessage();
        actual.setType(TypeOfMessage.MSG_TEXT);
        actual.setSenderId(0L);
        actual.setText("Text");
        assertEquals(actual, actual);

        TextMessage expected = new TextMessage();
        expected.setType(TypeOfMessage.MSG_TEXT);
        expected.setSenderId(0L);
        expected.setText("Text");
        assertEquals(actual, expected);

        expected.setType(TypeOfMessage.MSG_INFO);
        expected.setSenderId(0L);
        expected.setText("Text");
        assertNotEquals(actual, expected);

        expected.setType(TypeOfMessage.MSG_TEXT);
        expected.setSenderId(1L);
        expected.setText("Text");
        assertNotEquals(actual, expected);

        expected.setType(TypeOfMessage.MSG_TEXT);
        expected.setSenderId(0L);
        expected.setText("tEXT");
        assertNotEquals(actual, expected);

        String st = "String";
        assertNotEquals(actual, st);

        expected = null;
        assertNotEquals(actual, expected);
    }
}