package com.appteam.template.controller;

import com.appteam.template.data.Shop;
import com.appteam.template.data.Token;
import com.appteam.template.data.User;
import com.appteam.template.dto.ShopData;
import com.appteam.template.dto.TokenData;
import com.appteam.template.dto.UserData;
import com.appteam.template.oauth.CustomOAuth2User;
import com.appteam.template.repository.UserRepository;
import com.appteam.template.service.ShopService;
import com.appteam.template.service.TokenService;
import com.appteam.template.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.measure.unit.SystemOfUnits;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    ShopService shopService;

    @Autowired
    TokenService tokenService;

    public String getEmailFromRequest(final HttpServletRequest request) {
        TokenData tokenData = getTokenDataFromRequest(request);
        if (tokenData == null) {
            return "";
        } else {
            return tokenData.getEmail();
        }
    }

    public TokenData getTokenDataFromRequest(final HttpServletRequest request) {
        TokenData tokenData = null;
        System.out.println("request: " + request);
        for (Cookie cookie : request.getCookies()) {
            System.out.println(cookie.getName());
            if (Objects.equals(cookie.getName(), "token")) {
                try {
                    JSONObject json = new JSONObject(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                    String tokenKey = json.getString("token");
                    String email = json.getString("email");
                    tokenData = tokenService.getTokenByKey(tokenKey);
                    if (tokenData != null && Objects.equals(tokenData.getEmail(), email)) {
                        break;
                    }
                } catch (UnsupportedEncodingException e) {
                    continue;
                }
            }
        }
        return tokenData;
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(final HttpServletRequest request) {
        TokenData tokenData = getTokenDataFromRequest(request);
        if (tokenData == null) {
            return new ResponseEntity<>(false, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(tokenService.deleteToken(tokenData.getKey()), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<String> isAutorized(final HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        if (email.isEmpty()) {
            return new ResponseEntity<>("user is not autorized", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(email, HttpStatus.OK);
        }
    }

    @GetMapping("/delete_shop/{subdomain}")
    public ResponseEntity<Boolean> deleteShop(final @PathVariable String subdomain, final HttpServletRequest request) {
        if (getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(shopService.deleteShop(subdomain), HttpStatus.OK);
    }

    @GetMapping("/change_current_shop/{subdomain}")
    public ResponseEntity<Boolean> changeCurrentShop(final @PathVariable String subdomain, final HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        if (email.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        UserData userData = userService.getUserByEmail(email);
        if (userData == null || shopService.getShopBySubdomain(subdomain) == null ||
                !shopService.getShopBySubdomain(subdomain).getUser().getEmail().equals(email)) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        userData.setCurrentShop(subdomain);
        userService.saveUser(userData);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/shop-rights-page/{shopName}")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("redirectedUrl");
    }

    @GetMapping("/1")
    public void redirectWithUsingRedirectView(final HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (int i = 0; i < request.getCookies().length; i++) {
            System.out.println("cookie from /1: " + request.getCookies()[i].getName() + " " + request.getCookies()[i].getValue());
        }
        System.out.println("auth name from /1: " + ((CustomOAuth2User) auth.getPrincipal()).getEmail());
    }

    @GetMapping("/redirectedUrl")
    public String redirected(final HttpServletRequest request,
                             @RequestParam("code") String code,
                             @RequestParam("shop") String shop,
                             @RequestParam("state") String state) throws IOException, URISyntaxException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String url = "https://" + shop + "/admin/oauth/access_token?client_id=" + "62c60904ece30e9454ebd81fccc7882c" + "&client_secret=" + "30fbc33a2d764f0f1338cd2d94e95551" + "&code=" + code;
        String token = "";
        URI uri = new URI("https://" + shop + "/admin/oauth/access_token");
        HttpRequest requestToShopify = HttpRequest.newBuilder()
                .uri(java.net.URI.create(url))
//                .header("client_id", "62c60904ece30e9454ebd81fccc7882c")
//                .header("client_secret", "30fbc33a2d764f0f1338cd2d94e95551")
//                .header("code", code)
                .method("GET", java.net.http.HttpRequest.BodyPublishers.noBody())
                .build();
        System.out.println(requestToShopify);
        HttpResponse<String> response = HttpClient.newHttpClient().send(requestToShopify, HttpResponse.BodyHandlers.ofString());

//        JSONObject responseObject = new JSONObject(response.body());
//        System.out.println(responseObject);
        System.out.println("auth name: " + ((CustomOAuth2User) auth.getPrincipal()).getEmail());
        UserData userData = userService.getUserByEmail(((CustomOAuth2User) auth.getPrincipal()).getEmail());
        ShopData shopToSave = new ShopData(shop, token, new User(userData), new ArrayList<>());
        shopService.saveShop(shopToSave);

        return "hello";
    }
}
