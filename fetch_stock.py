import asyncio
import websockets
import yfinance as yf
import json

# List of Indian stock tickers (Yahoo Finance format)
tickers = ['TATAMOTORS.NS', 'RELIANCE.NS', 'INFY.NS', 'HDFCBANK.NS', 'ITC.NS']

async def stock_server(websocket):
    await websocket.send("✅ Connected to stock server.")
    while True:
        stock_data = {}
        for ticker in tickers:
            try:
                stock = yf.Ticker(ticker)
                price = stock.info['regularMarketPrice']
                stock_data[ticker] = price
            except Exception as e:
                stock_data[ticker] = f"Error: {e}"

        await websocket.send(json.dumps(stock_data))
        await asyncio.sleep(5)

async def main():
    async with websockets.serve(stock_server, "localhost", 9090):
        print("🚀 WebSocket server started at ws://localhost:9090")
        await asyncio.Future()  # Run forever

if __name__ == "__main__":
    asyncio.run(main())
