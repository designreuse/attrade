package by.attrade.config;

import by.attrade.config.exception.Oauth2ConfigMismatchEntryUrlException;
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
    @Setter
    @Getter
    private Oauth2Config oauth2Config;

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
        int i = getIndexSocial(map);
        loadUser(map, oauth2Config.getSocial().get(i), oauth2Config.getIdAttr().get(i) , oauth2Config.getNameAttr().get(i));
//        if (userInfoEndpointUrl.contains("google")){
//            loadUser(map, "google", "sub", "email");
//        }else if(userInfoEndpointUrl.contains("facebook")){
//            loadUser(map, "facebook", "id", "name");
//        }else if(userInfoEndpointUrl.contains("github")){
//            loadUser(map, "github", "id", "login");
//        }
//        else {
//            throw new Oauth2LoaderMissedException("Social provider is not supported there.");
//        }
        return extractAuthentication(map);
    }

    private int getIndexSocial(Map<String, Object> map) {
        List<String> socials = oauth2Config.getSocial();
        int size = socials.size();
        for (int i = 0; i < size; i++) {
            if (userInfoEndpointUrl.contains(socials.get(i))){
                return i;
            }
        }
        throw new Oauth2ConfigMismatchEntryUrlException("Socials in config are not " + userInfoEndpointUrl);
    }

    private void loadUser(Map<String, Object> map, String social, String idAttr, String nameAttr) {
        if (map.containsKey(idAttr)) {
            String sub = social + map.get(idAttr);
            User userFromDB = userService.findBySub(sub);
            User user = Optional.ofNullable(userFromDB).orElseGet(() -> getUser(map, sub, nameAttr));
            userService.save(user);
        }else{
            throw new Oauth2TokenChangedException("Key " + idAttr + "is missed." + "Map contains: "+map.entrySet());
        }
    }

    private User getUser(Map<String, Object> map, String sub, String nameAttr) {
        String name = (String) map.get(nameAttr);
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