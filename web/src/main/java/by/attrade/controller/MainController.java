package by.attrade.controller;

import by.attrade.config.ServerPathConfig;
import by.attrade.controller.utils.ControllerUtils;
import by.attrade.domain.Message;
import by.attrade.domain.User;
import by.attrade.repos.MessageRepo;
import by.attrade.service.MessageSourceOnlyLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    private final MessageRepo messageRepo;

    @Autowired
    private ServerPathConfig serverPathConfig;
    @Autowired
    private MessageSourceOnlyLanguageService messageSourceOnlyLanguageService;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @PostMapping("/loginFailed")
    public String loginPage(
            @RequestParam(value = "username", required = false) String username,
            Model model,
            Locale locale
    ) {
        model.addAttribute("username", username);
        String error = messageSourceOnlyLanguageService.getMessage("loginFailed", null, locale);
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/")
    public String greeting(Authentication authentication) {
        return "greeting";
    }

    @GetMapping(value = "/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 32) Pageable pageable,
            Model model) {
        Page<Message> page;
        if (filter == null || filter.isEmpty()) {
            page = messageRepo.findAll(pageable);
        } else {
            page = messageRepo.findByTag(filter, pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping(value = "/main")
    public String addMessages(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    private void saveFile(
            @Valid Message message,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid + "." + file.getOriginalFilename();
            message.setFilename(resultFileName);
            file.transferTo(new File(serverPathConfig.getAbsolute() + serverPathConfig.getUpload() + "/" + resultFileName));
        }
    }

    @GetMapping("/user/messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 32) Pageable pageable,
            Model model) {
        Page<Message> page;
        page = messageRepo.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/user/messages/" + currentUser.getId());
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        return "userMessages";
    }

    @PostMapping("/user/messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message != null) {
            if (message.getAuthor().equals(currentUser)) {
                if (!StringUtils.isEmpty(text)) {
                    message.setText(text);
                }
                if (!StringUtils.isEmpty(tag)) {
                    message.setTag(tag);
                }
                saveFile(message, file);
            }
        }
        return "redirect:/user/messages/" + user;
    }
}