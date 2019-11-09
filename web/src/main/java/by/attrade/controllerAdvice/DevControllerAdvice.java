package by.attrade.controllerAdvice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DevControllerAdvice {
    @Value("${spring.profiles.active}")
    private String profile;
    @Value("${webpack.devPort}")
    private String webpackDevPort;

    @ModelAttribute
    public void handleRequest(HttpServletRequest request, Model model) {
        model.addAttribute("isDevMode", "dev".equals(profile));
        model.addAttribute("webpackDevPort", webpackDevPort);
    }
}
