const quotes = ["I have no special talent. I am only passionately curious-Albert Einstein.",
                "If you judge people, you have no time to love them-Mother Teresa",
                "Wisely, and slow. They stumble that run fast-William Shakespeare",
                "Stay hungry, stay foolish-Steve Jobs",
                "A great man is always willing to be little-Ralph Waldo Emerson",
                "The greatest wealth is to live content with little-Plato",
                "The successful warrior is the average man, with laser-like focus. Bruce Lee",
                ];

const net = require('net');
const server = net.createServer((c) => {
  // 'connection' listener.
  console.log('client connected');
  c.on('end', () => {
    console.log('client disconnected');
  });
  const day = new Date().getDay();
  c.end(`${quotes[day]}\r\n`);
});
server.on('error', (err) => {
  throw err;
});
server.listen(3014, () => {
  console.log('server bound');
});