package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.Main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Webhook {

    public static void send(String content) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Main.WEBHOOK))
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
