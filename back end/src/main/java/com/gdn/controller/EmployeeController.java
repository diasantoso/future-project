package com.gdn.controller;

import com.gdn.model.Employee;
import com.gdn.response.ResponseBack;
import com.gdn.response.employee.EmployeeResponse;
import com.gdn.response.employee.EmployeeResponseList;
import com.gdn.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Dias on 3/30/2017.
 */
@Controller
@RequestMapping(value = "/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> doLogin(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {

        String token = null;
        Employee employee = employeeService.findOneByEmail(email);
        System.out.println(email);
        Map<String, Object> tokenMap = new HashMap<String, Object>();

        if (employee != null && employee.getPassword().equals(password)) {
            token = Jwts.builder().setSubject(email).claim("role", employee.getRole()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            tokenMap.put("token", token);
            tokenMap.put("user", employee);

            return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);

        } else {
            tokenMap.put("token", null);
            return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
        }
    }

    //============================= CRUDS EMPLOYEE ================================

    /**
     * This method is used for user registration. Note: user registration is not
     * require any authentication.
     *
     *
     * @return
     */

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack createEmployee(@RequestBody EmployeeResponse param) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(param, emp);

        if(employeeService.findOneByEmail(emp.getEmail())!=null){
            throw new RuntimeException("Email is already used");
        }

        emp.setRole("Employee");
        emp.setStatus(1);
        Employee result = employeeService.save(emp);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success adding");
        else
            responseBack.setResponse("failed adding");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmployeeResponseList getAllActiveEmployees() {
        List<Employee> data = employeeService.getAll();
        List<EmployeeResponse> response = new ArrayList<>();
        EmployeeResponseList result = new EmployeeResponseList();

        for(Employee emp : data) {
            EmployeeResponse parse = new EmployeeResponse();
            BeanUtils.copyProperties(emp, parse);
            response.add(parse);
        }
        result.setValue(response);
        return result;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack updateEmployee(@RequestBody EmployeeResponse param) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(param, emp);
        Employee result = employeeService.save(emp);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success updating");
        else
            responseBack.setResponse("failed updating");

        return responseBack;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/employees", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseBack deleteEmployee(@RequestParam String id) {
        Employee result = employeeService.delete(id);

        ResponseBack responseBack = new ResponseBack();
        if(result!=null)
            responseBack.setResponse("success delete");
        else
            responseBack.setResponse("failed delete");

        return responseBack;
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmployeeResponse getOneActiveEmployee(@PathVariable("id") String id){
        Employee data = employeeService.getOneActive(id);
        EmployeeResponse result = new EmployeeResponse();

        BeanUtils.copyProperties(data , result);

        return result;
    }
}
