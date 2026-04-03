package com.example.skincareva;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class SupabaseService {
    private static final String URL = "https://rabxycgaxpprdpimqkbk.supabase.co";
    private static final String KEY = "sb_publishable_qemUZ3TlxkkGNAv_Abvf-A_ExHRENZS";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static boolean signIn(String email, String password) throws Exception{
        String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/auth/v1/token?grant_type=password"))
                .header("apikey", KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 200) {
            JsonObject root = gson.fromJson(resp.body(), JsonObject.class);
            JsonObject user = root.getAsJsonObject("user");
            String uuid = user.get("id").getAsString();

            UserSession.id = uuid;
            UserSession.email = user.get("email").getAsString();
            fetchUserProfile(uuid);
            return true;
        }
        return false;
    }

    public static boolean signUp(String email, String password, int age, String gender) throws Exception{
        String authJson = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        HttpRequest authReq = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/auth/v1/signup"))
                .header("apikey", KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(authJson))
                .build();

        HttpResponse<String> authResp = client.send(authReq, HttpResponse.BodyHandlers.ofString());

        if (authResp.statusCode() == 200 || authResp.statusCode() == 201) {
            JsonObject userData = gson.fromJson(authResp.body(), JsonObject.class);
            String userId = userData.get("id").getAsString();

            // 2. Создание профиля в таблице profiles
            String profileJson = String.format("{\"id\":\"%s\", \"email\":\"%s\", \"age\":%d, \"gender\":\"%s\"}",
                    userId, email, age, gender);

            HttpRequest profReq = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/rest/v1/profiles"))
                    .header("apikey", KEY)
                    .header("Authorization", "Bearer " + KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(profileJson))
                    .build();

            client.send(profReq, HttpResponse.BodyHandlers.ofString());
            return true;
        }
        return false;
    }

    public static List<Product> getProducts() throws Exception{
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/rest/v1/products?select=*"))
                .header("apikey", KEY)
                .header("Authorization", "Bearer " + KEY)
                .GET().build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        Product[] products = gson.fromJson(resp.body(), Product[].class);
        return List.of(products);
    }

    private static void fetchUserProfile(String uuid) throws Exception{
        HttpRequest reg = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/rest/v1/profiles?id=eq." + uuid + "&select=*"))
                .header("apikey", KEY)
                .header("Authorization", "Bearer " + KEY)
                .GET().build();
        HttpResponse<String> resp = client.send(reg, HttpResponse.BodyHandlers.ofString());
        JsonArray arr = gson.fromJson(resp.body(), JsonArray.class);

        if(arr.size() > 0){
            JsonObject p = arr.get(0).getAsJsonObject();
            UserSession.age = p.get("age").getAsInt();
            UserSession.gender = p.get("gender").getAsString();
        }
    }
}
