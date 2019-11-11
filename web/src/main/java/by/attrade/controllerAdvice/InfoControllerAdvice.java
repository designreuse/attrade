package by.attrade.controllerAdvice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class InfoControllerAdvice {
    @Value("${info.url}")
    private String infoUrl;
    @Value("${info.logo}")
    private String infoLogo;

    @ModelAttribute
    public void handleRequest(HttpServletRequest request, Model model) {
        model.addAttribute("infoUrl", "http://localhost:8080");
        model.addAttribute("infoLogo", infoLogo);
    }
}
