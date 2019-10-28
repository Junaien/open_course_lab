const server_address = "localhost";
const server_port = 8125;
let nickname;

const readline = require('readline');
const net = require('net');
const io = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});


const connectionData = function(data) {
  readline.clearLine(process.stdout, 0);
  readline.cursorTo(process.stdout, 0, null);
  console.log(data.toString());
  process.stdout.write(`[${nickname}]:`);
};

const connectionEnd = function() {
  console.log("client disconnected");
  process.exit();
};

const connectionStart = function() {
  io.question('Pick a nickname:', (answer) => {
    nickname = answer;
    chat();
  });
   
};

const chat = function() {
  io.question(`[${nickname}]:`, (message) => {
    if(message == "/quit") {
      client.end();
    } else {
      client.write(`[${nickname}]: ${message}`);
      chat();
    }
  });
};


const client = net.createConnection(server_port, server_address, connectionStart);
client.on('data', connectionData);
client.on('end', connectionEnd);




