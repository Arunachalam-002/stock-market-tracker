import asyncio
import websockets
import yfinance as yf
import json

tickers = [
    'RELIANCE.NS', 'TCS.NS', 'INFY.NS', 'HDFCBANK.NS', 'ICICIBANK.NS',
    'KOTAKBANK.NS', 'AXISBANK.NS', 'SBIN.NS', 'ITC.NS', 'LT.NS',
    'HINDUNILVR.NS', 'TATAMOTORS.NS', 'BAJFINANCE.NS', 'ASIANPAINT.NS',
    'SUNPHARMA.NS', 'WIPRO.NS', 'MARUTI.NS', 'NTPC.NS', 'TECHM.NS', 'POWERGRID.NS'
]

async def stock_server(websocket):
    while True:
        stock_data = {}
        for ticker in tickers:
            try:
                stock = yf.Ticker(ticker)
                info = stock.info
                stock_data[ticker] = {
                    "name": info.get("shortName", ticker),
                    "price": info.get("regularMarketPrice", "N/A")
                }
            except Exception as e:
                stock_data[ticker] = {
                    "name": ticker,
                    "price": f"Error"
                }
        await websocket.send(json.dumps(stock_data))
        await asyncio.sleep(5)

async def main():
    async with websockets.serve(stock_server, "localhost", 9090):
        print("🚀 WebSocket server started at ws://localhost:9090")
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
