package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    static Map<String,Hotel> hotelMap=new HashMap<>();
    static Map<Integer,User> userMap=new HashMap<>();
    static Map<Integer,List<Booking>> bookingMap=new HashMap<>();
    public static void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(),hotel);
        return;
    }

    public static Optional<Hotel> getHotelByName(String hotelName) {
        if(hotelMap.containsKey(hotelName)){
            return Optional.of(hotelMap.get(hotelName));
        }
        return Optional.empty();
    }

    public static void addUser(User user) {
        userMap.put(user.getaadharCardNo(), user);
        return;
    }

    public static ArrayList<Hotel> getListOfAllHotels() {
        return new ArrayList<Hotel>(hotelMap.values());
    }

    public static int getNoOfRoom(String hotelName) {
        return hotelMap.get(hotelName).getAvailableRooms();
    }

    public static void bookARoom(Booking booking) {
        List<Booking> bookingList=new ArrayList<>();
        if(bookingMap.containsKey(booking.getBookingAadharCard())){
            bookingList=bookingMap.get(booking.getBookingAadharCard());
        }
        bookingList.add(booking);
        bookingMap.put(booking.getBookingAadharCard(),bookingList);
        int p=hotelMap.get(booking.getHotelName()).getAvailableRooms();
        hotelMap.get(booking.getHotelName()).setAvailableRooms(p-booking.getNoOfRooms());
        return;
    }

    public static int getBookings(Integer aadharCard) {
        if(bookingMap.containsKey(aadharCard)){
            return bookingMap.get(aadharCard).size();
        }
        return -1;
    }
}
