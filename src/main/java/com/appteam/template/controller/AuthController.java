package com.appteam.template.controller;

import com.appteam.template.data.Shop;
import com.appteam.template.data.User;
import com.appteam.template.oauth.CustomOAuth2User;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Enumeration;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class AuthController {
    @GetMapping("/shop-rights-page/{shopName}")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("redirectedUrl");
    }

    @GetMapping("/1")
    public void redirectWithUsingRedirectView(final HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

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

        System.out.println(request.getCookies()[0].getName());
        System.out.println("auth name: " + ((CustomOAuth2User) auth.getPrincipal()).getEmail());

        Shop shopToSave = new Shop();
        shopToSave.setSubdomain(shop);
        shopToSave.setToken(token);
        User user = null; // get user by email
        shopToSave.setUser(user);
        // save shop to bd;

        return "hello";
    }
}
