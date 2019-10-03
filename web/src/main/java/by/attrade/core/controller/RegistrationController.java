package by.attrade.core.controller;

import by.attrade.core.controller.utils.ControllerUtils;
import by.attrade.core.domain.User;
import by.attrade.core.domain.dto.RecaptchaResponseDTO;
import by.attrade.core.service.MailSenderService;
import by.attrade.core.service.RecaptchaService;
import by.attrade.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final RecaptchaService recaptchaService;
    private final MailSenderService mailSenderService;
    @Value("${info.logo}")
    private String logo;

    @Autowired
    public RegistrationController(UserService userService, RecaptchaService recaptchaService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.recaptchaService = recaptchaService;
        this.mailSenderService = mailSenderService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String passwordConfirm,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        RecaptchaResponseDTO response = recaptchaService.getCaptchaResponseDTO(captchaResponse);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha.");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different.");
        }
        if (!response.isSuccess() || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            sendMessage(user);
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to " + logo + ". Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated.");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found.");
        }
        return "login";
    }
}
