package com.appteam.template.dto;

import com.appteam.template.data.Token;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenData {
    private String key;
    private String email;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public TokenData(){
    }

    public TokenData(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public TokenData(Token token) {
        this.key = token.getKey();
        this.email = token.getEmail();
    }


    public static TokenData generate(String email) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);
        return new TokenData(token, email);
    }

    public JSONObject toJSON() {
        Map<String, String> map = new HashMap<>();
        map.put("token", key);
        map.put("email", email);
        return new JSONObject(map);
    }

    public static TokenData fromJSON(String valueFromCookie) throws UnsupportedEncodingException {
        String value = URLDecoder.decode(valueFromCookie, "UTF-8");
        JSONObject jsonObject = new JSONObject(value);
        return new TokenData(jsonObject.getString("token"), jsonObject.getString("email"));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
