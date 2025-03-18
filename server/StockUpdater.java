import java.util.Timer;
import java.util.TimerTask;

public class StockUpdater {
    private static StockWebSocketServer webSocketServer = new StockWebSocketServer(8081);

    public static void main(String[] args) {
        webSocketServer.start();
        Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String stockPrice = StockFetcher.getStockPrice("AAPL");
                webSocketServer.broadcastStockUpdate("AAPL Price: " + stockPrice);
            }
        }, 0, 10000);
    }
}
