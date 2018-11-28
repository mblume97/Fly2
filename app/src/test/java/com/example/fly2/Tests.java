package com.example.fly2;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void find_min_returns_cheapest_ticket_pair() {
        TicketPair one = new TicketPair(null, null, 60);
        TicketPair two = new TicketPair(null, null, 100);
        LoadingActivity loadingActivity = new LoadingActivity();
        TicketPair min = loadingActivity.findMin(one, two);
        assertSame(one, min);
    }

    @Test
    public void create_date_from_string() {
        String dateStr = "11-11-2018";
        Date actual = DatePicker.createDate(dateStr);
        Date expected = new GregorianCalendar(2018, Calendar.NOVEMBER, 11).getTime();
        assertEquals(expected.getTime(), actual.getTime());
    }

    @Test
    public void format_string_for_api() {
        Date date = new GregorianCalendar(2018, Calendar.NOVEMBER, 11).getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        assertEquals("2018-11-11", formattedDate);
    }

    @Test
    public void create_api_url() {

        Airport firstAirport = new Airport("ORD");
        Airport secondAirport = new Airport("IAH");
        City destinationCity = new City("Chicago, IL");

        Date departureDate = new GregorianCalendar(2018, Calendar.NOVEMBER, 11).getTime();
        Date returnDate = new GregorianCalendar(2018, Calendar.DECEMBER, 11).getTime();

        Flight flight = new Flight(firstAirport, secondAirport, departureDate, returnDate, destinationCity);
        String expected = LoadingActivity.createURL(flight);
        String actual = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes" +
                "/v1.0/US/USD/en-US/ORD-sky/IAH-sky/2018-11-11/2018-12-11";
        assertEquals(expected, actual);

    }


}