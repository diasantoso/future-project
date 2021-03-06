package com.gdn.controller;

import com.gdn.model.Booking;
import com.gdn.model.Room;
import com.gdn.response.ResponseBack;
import com.gdn.response.room.RoomResponse;
import com.gdn.response.room.RoomResponseList;
import com.gdn.service.BookingService;
import com.gdn.service.OfficeService;
import com.gdn.service.RoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adin on 4/24/2017.
 */
@Controller
@RequestMapping(value = "/api")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    OfficeService officeService;
    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/rooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RoomResponseList getAllRooms() {
        List<Room> data = roomService.getAll();
        List<RoomResponse> response = new ArrayList<>();
        RoomResponseList result = new RoomResponseList();

        for(Room room : data) {
            RoomResponse parse = new RoomResponse();
            BeanUtils.copyProperties(room, parse);
            response.add(parse);
        }
        result.setValue(response);
        return result;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/rooms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack createRoom(@RequestBody RoomResponse param) {
        Room room = new Room();
        BeanUtils.copyProperties(param, room);
        //Make office data not changed to null (because room only have idOffice, it will make other attribute null)
        room.setOffice(officeService.getOneActive(room.getOffice().getIdOffice()));
        Room result = roomService.save(room);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            //Response set to idRoom, so RoomImage can get id Room from here
            responseBack.setResponse(result.getIdRoom());
        else
            responseBack.setResponse("failed adding");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/rooms", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack updateRoom(@RequestBody RoomResponse param) {
        Room room = new Room();
        BeanUtils.copyProperties(param, room);
        Room result = roomService.save(room);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success updating");
        else
            responseBack.setResponse("failed updating");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/rooms", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack deleteRoom(@RequestParam String id) {
        Room result = roomService.delete(id);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success delete");
        else
            responseBack.setResponse("failed delete");

        return responseBack;
    }

    //Mapping to get one rooms based on their ID
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RoomResponse getOneActiveRoom(@PathVariable ("id") String id) {
        Room data = roomService.getOneActive(id);
        RoomResponse result = new RoomResponse();

        if(data!=null)
            BeanUtils.copyProperties(data,result);

        return result;
    }

    @RequestMapping(value = "/rooms/available", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RoomResponseList getAvailableRoom(@RequestParam java.sql.Date date, @RequestParam Time startTime,
                                             @RequestParam Time endTime, @RequestParam String officeId) throws ParseException {

        List<Room> data = roomService.getAllActive();
        List<Room> data_used = new ArrayList<>();
        List<RoomResponse> responses = new ArrayList<>();
        RoomResponseList result = new RoomResponseList();

        //Get Unavailable Room
        List<Booking> data_book = bookingService.getAllBooking();

        for (Booking book : data_book) {
            //(book.startTime >= startTime && book.startTime <endTime)
            //(book.endTime <= endTime && book.endTime > startTime)

            if ((book.getDateMeeting().equals(date) &&
                    (((book.getStartTime().equals(startTime) || book.getStartTime().before(startTime)) && book.getEndTime().after(startTime)) ||
                            ((book.getEndTime().equals(endTime) || book.getEndTime().after(endTime) && book.getStartTime().before(endTime)))))
                    && book.getStatus()==1) {
                data_used.add(book.getRoom());
            }
        }

        //Get Available Room
        data.removeAll(data_used);

        for(Room room : data){
            RoomResponse parse = new RoomResponse();
            BeanUtils.copyProperties(room, parse);
            if(officeId.equals(room.getOffice().getIdOffice())) {
                responses.add(parse);
            }
        }

        result.setValue(responses);
        System.out.println("## Room Available : "+data.size());
        System.out.println("## Room Booked    : "+data_used.size());
        return result;
    }
}
