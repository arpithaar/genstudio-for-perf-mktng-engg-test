package com.adobe.roman_numeral_conversion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle redirection logic
 */
@Controller
class RedirectController {

    @RequestMapping("/")
    public String redirect(Model model) {
        model.addAttribute("message", "Welcome to Spring Boot Application");
        return "redirectMessage";  // The view resolver maps this to "redirect.html"
    }
}
