import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class StockFetcher {
    private static final String API_URL = "https://api.example.com/stock";

    public static String getStockPrice(String symbol) {
        try {
            URL url = new URL(API_URL + "?symbol=" + symbol);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject json = new JSONObject(response);
            return json.getString("price");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error fetching price";
        }
    }
}
