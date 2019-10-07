package by.attrade.controller;

import by.attrade.controller.utils.ControllerUtils;
import by.attrade.domain.User;
import by.attrade.domain.VerificationToken;
import by.attrade.domain.dto.RecaptchaResponseDTO;
import by.attrade.service.MailSenderService;
import by.attrade.service.RecaptchaService;
import by.attrade.service.UserService;
import by.attrade.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.Optional;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final RecaptchaService recaptchaService;
    private final MailSenderService mailSenderService;
    private final VerificationTokenService verificationTokenService;
    @Value("${info.mainURL}")
    private String mainURL;

    @Autowired
    public RegistrationController(UserService userService, RecaptchaService recaptchaService, MailSenderService mailSenderService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.recaptchaService = recaptchaService;
        this.mailSenderService = mailSenderService;
        this.verificationTokenService = verificationTokenService;
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
            model.addAttribute("captchaError", "Заполните reCAPTCHA.");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли не совпадают.");
        }
        if (!response.isSuccess() || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Юзер с данным логином уже существует!");
            return "registration";
        }
        sendVerificationToken(user);
        return "redirect:/activation";
    }

    private void sendVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenService.save(verificationToken);
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Приветствуем Вас, %s! \n" +
                            "Вы зарегистрировались на " + mainURL + "\n" +
                            "Код активации:\t %s",
                    user.getUsername(),
                    verificationToken.getToken()
            );
            mailSenderService.send(user.getEmail(), "Активация аккаунта", message);
        }
    }

    @GetMapping("/activation")
    public String activation() {
        return "activation";
    }

    @PostMapping("/activate")
    public String activate(Model model, @RequestParam String code) {
        Optional<VerificationToken> opt = verificationTokenService.findById(code);
        if(opt.isPresent()){
            VerificationToken token = opt.get();
            if(token.isExpiredToken()){
                return "activationExpired";
            }
            User user = token.getUser();
            user.setActive(true);
            userService.save(user);
            verificationTokenService.deleteById(code);
            return "activationSuccess";
        }
        model.addAttribute("activationError", "Введен неверный код");
        return "activation";
    }
    @GetMapping("/activationAgain")
    public String activationAgain(@AuthenticationPrincipal User user){
        sendVerificationToken(user);
        return "activation";
    }
}
