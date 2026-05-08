package com.project2.wealthmanagement.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping(value = {
        "/",
        "/{path:^(?!api|assets).*$}",
        "/{path:^(?!api|assets).*$}/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
