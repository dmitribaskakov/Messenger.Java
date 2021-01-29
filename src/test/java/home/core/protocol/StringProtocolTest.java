package home.core.protocol;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StringProtocolTest {

    static final Map<String, String> testData = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        testData.put("123", "123");
    }

    @Test
    public void decode() {
    }

    @Test
    public void encode() {
    }
}