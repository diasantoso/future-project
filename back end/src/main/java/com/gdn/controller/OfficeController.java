package com.gdn.controller;

import com.gdn.model.Office;
import com.gdn.response.ResponseBack;
import com.gdn.response.office.OfficeResponse;
import com.gdn.response.office.OfficeResponseList;
import com.gdn.service.OfficeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dias on 3/30/2017.
 */
@Controller
@RequestMapping(value = "/api")
public class OfficeController {

    @Autowired
    OfficeService officeService;

    @RequestMapping(value = "/offices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OfficeResponseList getAllOffices() {
        List<Office> data = officeService.getAll();
        List<OfficeResponse> response = new ArrayList<>();
        OfficeResponseList result = new OfficeResponseList();

        for(Office office : data) {
            OfficeResponse parse = new OfficeResponse();
            BeanUtils.copyProperties(office, parse);
            response.add(parse);
        }
        result.setValue(response);
        return result;
    }

    @RequestMapping(value = "/offices/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OfficeResponseList getAllActiveOffices() {
        List<Office> data = officeService.getAllActive();
        List<OfficeResponse> response = new ArrayList<>();
        OfficeResponseList result = new OfficeResponseList();

        for(Office office : data) {
            OfficeResponse parse = new OfficeResponse();
            BeanUtils.copyProperties(office, parse);
            response.add(parse);
        }
        result.setValue(response);
        return result;
    }

    @RequestMapping(value = "/offices/nonauth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OfficeResponseList getAllActiveOfficesNonAuth() {
        List<Office> data = officeService.getAllActive();
        List<OfficeResponse> response = new ArrayList<>();
        OfficeResponseList result = new OfficeResponseList();

        for(Office office : data) {
            OfficeResponse parse = new OfficeResponse();
            BeanUtils.copyProperties(office, parse);
            response.add(parse);
        }
        result.setValue(response);
        return result;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/offices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack createOffice(@RequestBody OfficeResponse param) {
        Office office = new Office();
        BeanUtils.copyProperties(param, office);
        Office result = officeService.save(office);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success adding");
        else
            responseBack.setResponse("failed adding");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/offices", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack updateOffice(@RequestBody OfficeResponse param) {
        Office office = new Office();
        BeanUtils.copyProperties(param, office);
        Office result = officeService.save(office);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success updating");
        else
            responseBack.setResponse("failed updating");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/offices", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack deleteOffice(@RequestParam String id) {
        Office result = officeService.delete(id);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success delete");
        else
            responseBack.setResponse("failed delete");

        return responseBack;
    }

    ////Mapping to get one offices based on their ID
    //Get one offices -- for get data to form when update
    //can't use param bcs path /offices had been used on get all active offices methods
    @RequestMapping(value = "/offices/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OfficeResponse getOneActiveOffice(@PathVariable("id") String id){
        Office data = officeService.getOneActive(id);
        OfficeResponse result = new OfficeResponse();

        BeanUtils.copyProperties(data , result);

        return result;
    }
}
