package by.attrade.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/product/management")
public class ProductManagementController {
    @GetMapping ()
    public String productManagement(){return "productManagement";}
}
