package com.blibli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ADIN on 4/22/2017.
 */


@Controller
public class AppController {

    //likely... this calls the folders
    @RequestMapping("/offices")
    String office(ModelMap modal) {
        modal.addAttribute("title","Bliblibli");
        return "office/index";
    }

    @RequestMapping("/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        return "office/"+page;
    }

}

