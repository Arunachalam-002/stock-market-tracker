const socket = new WebSocket("ws://localhost:8080");

socket.onopen = function () {
    console.log("Connected to WebSocket server.");
};

socket.onmessage = function (event) {
    console.log("Received: " + event.data);
    document.getElementById("stockPrice").innerText = event.data; // Update stock price on UI
};

socket.onerror = function (error) {
    console.error("WebSocket Error: ", error);
};

socket.onclose = function () {
    console.log("WebSocket connection closed.");
};
