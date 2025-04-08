const ws = new WebSocket("ws://localhost:9090");

ws.onopen = () => {
    console.log("✅ Connected to WebSocket server.");
};

ws.onmessage = (event) => {
    try {
        const data = JSON.parse(event.data);
        const output = document.getElementById("stockOutput");
        output.innerHTML = ""; // Clear previous data

        for (const [ticker, price] of Object.entries(data)) {
            output.innerHTML += `<p><strong>${ticker}</strong>: ₹${price}</p>`;
        }
    } catch (e) {
        console.log("📩", event.data);
    }
};

ws.onclose = () => {
    console.log("🔌 Disconnected from WebSocket server.");
};
