window.addEventListener('DOMContentLoaded', () => {
    const logoMap = {
      "RELIANCE.NS": "https://logo.clearbit.com/reliance.com",
      "TCS.NS": "https://logo.clearbit.com/tcs.com",
      "INFY.NS": "https://logo.clearbit.com/infosys.com",
      "HDFCBANK.NS": "https://logo.clearbit.com/hdfcbank.com",
      "ICICIBANK.NS": "https://logo.clearbit.com/icicibank.com",
      "KOTAKBANK.NS": "https://logo.clearbit.com/kotak.com",
      "AXISBANK.NS": "https://logo.clearbit.com/axisbank.com",
      "SBIN.NS": "https://logo.clearbit.com/onlinesbi.com",
      "ITC.NS": "https://logo.clearbit.com/itcportal.com",
      "LT.NS": "https://logo.clearbit.com/larsentoubro.com",
      "HINDUNILVR.NS": "https://logo.clearbit.com/hul.co.in",
      "TATAMOTORS.NS": "https://logo.clearbit.com/tatamotors.com",
      "BAJFINANCE.NS": "https://logo.clearbit.com/bajajfinserv.in",
      "ASIANPAINT.NS": "https://logo.clearbit.com/asianpaints.com",
      "SUNPHARMA.NS": "https://logo.clearbit.com/sunpharma.com",
      "WIPRO.NS": "https://logo.clearbit.com/wipro.com",
      "MARUTI.NS": "https://logo.clearbit.com/marutisuzuki.com",
      "NTPC.NS": "https://logo.clearbit.com/ntpc.co.in",
      "TECHM.NS": "https://logo.clearbit.com/techmahindra.com",
      "POWERGRID.NS": "https://logo.clearbit.com/powergrid.in"
    };
  
    const socket = new WebSocket("ws://localhost:9090");
  
    const stockGrid = document.getElementById("stockGrid");
    const searchInput = document.getElementById("searchInput");
    let stockDataGlobal = {};
  
    socket.onopen = () => {
      console.log("📩 Connected to WebSocket server.");
    };
  
    socket.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        if (typeof data === "object") {
          stockDataGlobal = data;
          displayStocks(data);
        }
      } catch (error) {
        console.error("❌ Error parsing message:", error);
      }
    };
  
    searchInput.addEventListener("input", () => {
      const filtered = Object.fromEntries(
        Object.entries(stockDataGlobal).filter(([symbol]) =>
          symbol.toLowerCase().includes(searchInput.value.toLowerCase()) ||
          (stockDataGlobal[symbol].name?.toLowerCase().includes(searchInput.value.toLowerCase()))
        )
      );
      displayStocks(filtered);
    });
  
    function displayStocks(data) {
      stockGrid.innerHTML = "";
  
      Object.entries(data).forEach(([symbol, stockInfo]) => {
        const name = stockInfo.name || symbol;
        const price = stockInfo.price || "N/A";
        const logoURL = logoMap[symbol] || "https://via.placeholder.com/40";
  
        const card = document.createElement("div");
        card.className =
          "bg-gray-800 p-5 rounded-2xl shadow-md flex items-center space-x-4 hover:bg-gray-700 transition";
  
        card.innerHTML = `
          <img src="${logoURL}" alt="${name} Logo" class="w-12 h-12 rounded-full bg-white p-1 shadow" />
          <div>
            <h2 class="text-xl font-semibold text-cyan-400">${name}</h2>
            <p class="text-sm text-gray-300">Symbol: ${symbol}</p>
            <p class="text-white mt-1 text-lg font-bold">₹ ${price}</p>
          </div>
        `;
        stockGrid.appendChild(card);
      });
    }
  });
  