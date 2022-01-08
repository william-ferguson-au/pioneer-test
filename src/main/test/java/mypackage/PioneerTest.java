package mypackage;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Issue575 {

    static final String LOCALE_PROVIDERS_PROPERTY_KEY = "java.locale.providers";
    static final String LOCALE_PROVIDERS_PROPERTY_VALUE = "HOST,JRE,SPI";

    static final String DATE_STRING = "2018-07-22T06:20:30.123";
    static final Date EXPECTED_DATE = new Date(1532240430123L);

    @Order(1)
    @Test
    void test_null() throws Exception {
        assertNull(System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));

        final StdDateFormat stdDateFormat = new StdDateFormat();
        Date date = stdDateFormat.parse(DATE_STRING);

        assertEquals(EXPECTED_DATE, date);
    }

    @Order(2)
    @Test
    void test_system() throws Exception {
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
        assertEquals(LOCALE_PROVIDERS_PROPERTY_VALUE, System.getProperty(LOCALE_PROVIDERS_PROPERTY_KEY));

        final StdDateFormat stdDateFormat = new StdDateFormat();
        Date date = stdDateFormat.parse(DATE_STRING);

        assertEquals(EXPECTED_DATE, date);
    }

}