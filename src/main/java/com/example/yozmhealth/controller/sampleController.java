package com.example.yozmhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class sampleController {

    @GetMapping("/test")
    public ModelAndView sample() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/test");
        return mv;
    }
}
