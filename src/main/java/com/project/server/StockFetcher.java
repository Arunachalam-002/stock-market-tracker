package com.project.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class StockFetcher {
    private static final String API_URL = "https://api.example.com/stock";

    public static String getStockPrice(String symbol) {
        try {
            URL url = new URL(API_URL + "?symbol=" + symbol);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());
            return json.getString("price");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching price";
        }
    }
}
