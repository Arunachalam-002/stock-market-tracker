const socket = new WebSocket("ws://localhost:8081");

socket.onopen = () => {
    console.log("Connected to WebSocket server");
};

socket.onmessage = (event) => {
    document.getElementById("stockPrice").innerText = event.data;
};

socket.onclose = () => {
    console.log("Disconnected from WebSocket server");
};
