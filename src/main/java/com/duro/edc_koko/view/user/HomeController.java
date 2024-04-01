package com.duro.edc_koko.view.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String userIndex() {
        return "user/index";
    }


}
