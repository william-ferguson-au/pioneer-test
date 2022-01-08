package mypackage;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Issue575Test {

    static final String LOCALE_PROVIDERS_PROPERTY_KEY = "java.locale.providers";
    static final String LOCALE_PROVIDERS_PROPERTY_VALUE = "HOST,JRE,SPI";

    static final String DATE_STRING = "2018-07-22T06:20:30.123";
    static final Date EXPECTED_DATE = new Date(1532240430123L);

    @Order(1)
    @Test
    @ClearSystemProperty(key = LOCALE_PROVIDERS_PROPERTY_KEY)
    void test_null() throws Exception {
        System.out.println("java.version=" + System.getProperty("java.version"));
        System.out.println("java.locale.providers=" + System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));
        assertNull(System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));

        final StdDateFormat stdDateFormat = new StdDateFormat();
        Date date = stdDateFormat.parse(DATE_STRING);

        assertEquals(EXPECTED_DATE, date);
    }

    @Order(2)
    @Test
    void test_system() throws Exception {
        System.out.println("java.locale.providers=" + System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));
        System.setProperty(LOCALE_PROVIDERS_PROPERTY_KEY, LOCALE_PROVIDERS_PROPERTY_VALUE);

        final StdDateFormat stdDateFormat = new StdDateFormat();
        Date date = stdDateFormat.parse(DATE_STRING);

        assertEquals(EXPECTED_DATE, date);

        System.clearProperty(LOCALE_PROVIDERS_PROPERTY_KEY);
    }

    @Order(3)
    @Test
    @SetSystemProperty(key = LOCALE_PROVIDERS_PROPERTY_KEY, value = LOCALE_PROVIDERS_PROPERTY_VALUE)
    void test_pioneer() throws Exception {
        System.out.println("java.locale.providers=" + System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));
        assertEquals(LOCALE_PROVIDERS_PROPERTY_VALUE, System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));

        final StdDateFormat stdDateFormat = new StdDateFormat();
        Date date = stdDateFormat.parse(DATE_STRING);

        assertEquals(EXPECTED_DATE, date);
    }
}