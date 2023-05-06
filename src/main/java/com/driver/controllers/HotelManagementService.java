package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelManagementService {
    public static String addHotel(Hotel hotel) {
        Optional<Hotel> op1=HotelManagementRepository.getHotelByName(hotel.getHotelName());
        if(op1.isEmpty()) {
            HotelManagementRepository.addHotel(hotel);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public static Integer addUser(User user) {
        HotelManagementRepository.addUser(user);
        return user.getaadharCardNo();
    }

    public static String getHotelWithMostFacilities() {
        ArrayList<Hotel> hotels=HotelManagementRepository.getListOfAllHotels();
        String s1="";
        for(Hotel x: hotels){
            if(x.getFacilities().size()>s1.length() && x.getHotelName().compareTo(s1)>0){
                s1=x.getHotelName();
            }
        }
        return s1;
    }

    public static int bookARoom(Booking booking) {
        int noOfRoomsAvailable=HotelManagementRepository.getNoOfRoom(booking.getHotelName());
        if(booking.getNoOfRooms()>=noOfRoomsAvailable){
            String bookingIID= String.valueOf(UUID.randomUUID());
            booking.setBookingId(bookingIID);
            HotelManagementRepository.bookARoom(booking);
            Optional<Hotel> op2=HotelManagementRepository.getHotelByName(booking.getHotelName());
            return ((op2.get().getPricePerNight())*(booking.getNoOfRooms()));
        }
        return -1;
    }

    public static int getBookings(Integer aadharCard) {
        return HotelManagementRepository.getBookings(aadharCard);
    }

    public static Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Optional<Hotel> op3=HotelManagementRepository.getHotelByName(hotelName);
        List<Facility> facilityList=op3.get().getFacilities();
        for(Facility x: newFacilities){
            facilityList.add(x);
        }
        op3.get().setFacilities(facilityList);
        return op3.get();
    }
}