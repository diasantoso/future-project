package com.gdn.service;

import com.gdn.model.Booking;
import com.gdn.repository.booking.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Dias on 4/8/2017.
 */
@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> getAllBooking() {
        return bookingRepository.showAll();
    }

    public List<Booking> getById(String id) {
        return bookingRepository.showById(id);
    }

    public Booking save(Booking booking) {
        Booking result = bookingRepository.save(booking);
        return result;
    }

    public Booking delete(String id) {
        Booking result = bookingRepository.deleteBooking(id);
        return result;
    }

    public Booking find(String idBooking) {
        Booking result = bookingRepository.findOne(idBooking);
        return result;
    }

    public  List<Booking> getByEmpId(String empId){ return bookingRepository.showByEmployeeId(empId);}

    public BigInteger count() {
        return bookingRepository.countBooking();
    }

    public Booking getBookingByTicket(String ticket) {
        return bookingRepository.getBookingByTicket(ticket);
    }
}
