package by.attrade.config;

import by.attrade.config.exception.Oauth2LoaderMissedException;
import by.attrade.config.exception.Oauth2TokenChangedException;
import by.attrade.domain.User;
import by.attrade.service.UserService;
import by.attrade.type.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class CustomUserInfoTokenServices implements ResourceServerTokenServices {
    private final String userInfoEndpointUrl;
    private final String clientId;
    private OAuth2RestOperations restTemplate;
    private String tokenType = "Bearer";
    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
    private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();
    @Setter
    @Getter
    private UserService userService;

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.clientId = clientId;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
        this.authoritiesExtractor = authoritiesExtractor;
    }

    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
        this.principalExtractor = principalExtractor;
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Map<String, Object> map = getMap(userInfoEndpointUrl, accessToken);
        if (map.containsKey("error")) {
            log.debug("userinfo returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        }
        if (userInfoEndpointUrl.contains("google")){
            loadGoogleUser(map);
        }else if(userInfoEndpointUrl.contains("facebook")){
            loadFacebookUser(map);
        }else if(userInfoEndpointUrl.contains("github")){
            loadGithubUser(map);
        }
        else {
            throw new Oauth2LoaderMissedException("Social provider is not supported there.");
        }
        return extractAuthentication(map);
    }
    private void loadGoogleUser(Map<String, Object> map) {
        String id = "sub";
        if (map.containsKey(id)) {
            String sub = "google" + map.get(id);
            User userFromDB = userService.findBySub(sub);
            User user = Optional.ofNullable(userFromDB).orElseGet(() -> getGoogleUser(map, sub));
            userService.save(user);
        }else{
            throw new Oauth2TokenChangedException("Key " + id + "is missed." + "Map contains: "+map.entrySet());
        }
    }

    private void loadFacebookUser(Map<String, Object> map) {
        String id = "id";
        if (map.containsKey(id)) {
            String sub = "facebook" + map.get(id);
            User userFromDB = userService.findBySub(sub);
            User user = Optional.ofNullable(userFromDB).orElseGet(() -> getFacebookUser(map, sub));
            userService.save(user);
        }else{
            throw new Oauth2TokenChangedException("Key " + id + "is missed." + "Map contains: "+map.entrySet());
        }
    }
    private void loadGithubUser(Map<String, Object> map) {
        String id = "id";
        if (map.containsKey(id)) {
            String sub = "github" + map.get(id);
            User userFromDB = userService.findBySub(sub);
            User user = Optional.ofNullable(userFromDB).orElseGet(() -> getGithubUser(map, sub));
            userService.save(user);
        }else{
            throw new Oauth2TokenChangedException("Key " + id + "is missed." + "Map contains: "+map.entrySet());
        }
    }

    private User getGoogleUser(Map<String, Object> map, String sub) {
        Boolean active = (Boolean) map.get("email_verified");
        String email = (String) map.get("email");
        User newUser = new User();
        newUser.setUsername(email);
        newUser.setPassword("1Aa".concat(UUID.randomUUID().toString()));
        newUser.setSub(sub);
        newUser.setEmail(email);
        newUser.setActive(active);
        newUser.setRoles(Collections.singleton(Role.USER));
        return newUser;
    }

    private User getFacebookUser(Map<String, Object> map, String sub) {
        String name = (String) map.get("name");
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setPassword("1Aa".concat(UUID.randomUUID().toString()));
        newUser.setSub(sub);
        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));
        return newUser;
    }
    private User getGithubUser(Map<String, Object> map, String sub) {
        String name = (String) map.get("login");
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setPassword("1Aa".concat(UUID.randomUUID().toString()));
        newUser.setSub(sub);
        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));
        return newUser;
    }

    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = this.getPrincipal(map);
        List authorities = this.authoritiesExtractor.extractAuthorities(map);
        OAuth2Request request = new OAuth2Request(
                null,
                clientId,
                null,
                true,
                null,
                null,
                null,
                null,
                null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        token.setDetails(map);
        return new OAuth2Authentication(request, token);
    }

    protected Object getPrincipal(Map<String, Object> map) {
        Object principal = this.principalExtractor.extractPrincipal(map);
        return principal == null ? "unknown" : principal;
    }

    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private Map<String, Object> getMap(String path, String accessToken) {
        log.debug("Getting user info from: " + path);
        try {
            Object ex = this.restTemplate;
            if (ex == null) {
                BaseOAuth2ProtectedResourceDetails existingToken = new BaseOAuth2ProtectedResourceDetails();
                existingToken.setClientId(this.clientId);
                ex = new OAuth2RestTemplate(existingToken);
            }

            OAuth2AccessToken existingToken1 = ((OAuth2RestOperations) ex).getOAuth2ClientContext().getAccessToken();
            if (existingToken1 == null || !accessToken.equals(existingToken1.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
                token.setTokenType(this.tokenType);
                ((OAuth2RestOperations) ex).getOAuth2ClientContext().setAccessToken(token);
            }

            return (Map) ((OAuth2RestOperations) ex).getForEntity(path, Map.class, new Object[0]).getBody();
        } catch (Exception var6) {
            log.warn("Could not fetch user details: " + var6.getClass() + ", " + var6.getMessage());
            return Collections.singletonMap("error", "Could not fetch user details");
        }
    }
}