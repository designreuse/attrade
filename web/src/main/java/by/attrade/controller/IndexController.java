package by.attrade.controller;

import by.attrade.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public IndexController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping()
    public String index(Model model){
       model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";}
}
