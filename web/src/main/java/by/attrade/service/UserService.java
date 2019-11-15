package by.attrade.service;

import by.attrade.domain.User;
import by.attrade.repos.UserRepo;
import by.attrade.service.exception.UserAlreadyExistsException;
import by.attrade.service.validation.UserEmailValidationService;
import by.attrade.service.validation.UserPasswordValidationService;
import by.attrade.type.Role;
import by.attrade.util.ErrorWrapper;
import by.attrade.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPasswordValidationService userPasswordValidationService;
    @Autowired
    private UserEmailValidationService userEmailValidationService;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        return user;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public User loadUserByEmail(String s) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        return user;
    }

    public User findBySub(String sub) {
        return userRepo.findBySub(sub);
    }

    public List<Pair<String, Exception>> register(User user){
        List<Pair<String,Exception>> pairs = new ArrayList<>();
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            pairs.add(Pair.of("usernameError", new UserAlreadyExistsException("Аккаунт с данным email уже существует!")));
        }
        ErrorWrapper.runAndWrap("passwordError", ()-> userPasswordValidationService.validate(user.getPassword()), pairs);
        ErrorWrapper.runAndWrap("emailError", ()-> userEmailValidationService.validate(user.getEmail()),pairs);
        if(pairs.isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
        }
        return pairs;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepo.save(user);
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }
}
