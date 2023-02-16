package com.blackoutburst.workshop.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Webhook {

    public static void send(String content) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://discord.com/api/webhooks/1036038301158740018/jO8p5zO1S372shcX2uHcmtS6pvV90ArNOGDNVypK15S_PQYCFvns1t3mAt7FCqqI6Nkp"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"content\": \""+ content +"\"}"))
                    .build();

            client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
