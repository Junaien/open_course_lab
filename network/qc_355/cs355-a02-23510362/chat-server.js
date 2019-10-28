"use strict";
let clients = [];
const net = require("net");

const connectionListener = function (socket) {
  const connectionEnd = function () {
    clients = clients.filter(function (e) {return socket.remoteAddress != e.remoteAddress || 
                                        socket.remotePort != e.remotePort;});
    console.log("client disconnected");
  };

  const connectionError = function (err) {
    console.log(err);
    throw err;
  };

  const connectionData = function (data) {
    console.log("New Data Received!");
    console.log(data.toString());
    for (let c = 0; c < clients.length; c++) {
      if (clients[c].remoteAddress != this.remoteAddress ||
          clients[c].remotePort != this.remotePort) {
        clients[c].write(data);
      }
    }
  }
  socket.on("end",  connectionEnd);
  socket.on("error", connectionError);
  socket.on("data", connectionData);
  clients.push(socket);
  console.log(socket.remotePort);
  console.log("client connected");
};

const server = net.createServer(connectionListener);

const connectionError = function (err) {
  console.log(err);
  throw err;
};

server.on("error", connectionError);

const connectionStart = function () {
  console.log("server bound");
};

server.listen(8125, connectionStart);