package edu.ucsb.cs56.ratcalc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String getHomepage() {
        logger.info("Getting Home Page");
        return "index";
    }

}
