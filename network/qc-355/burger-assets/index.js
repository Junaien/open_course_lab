const fs = require("fs");
const url = require("url");
const http = require("http");
const Jimp = require("jimp");
const port = 3000;
const host = "localhost";

const open_assets = function () {
  const assets = [
    {id:"b", path: "assets/bun.png", resource: null},
    {id:"p", path: "assets/patty.png", resource: null},
    {id:"c", path: "assets/cheese.png", resource: null},
    {id:"l", path: "assets/lettuce.png", resource: null},
    {id:"t", path: "assets/tomato.png", resource: null},
    {id:"e", path: "assets/egg.png", resource: null}
  ];
  let assets_read = 0;
  const open_asset = function (asset) {
    Jimp.read(asset.path, (err, resource) => {
      if (err) {
        throw err;
      }
      asset.resource = resource;
      assets_read += 1;
      if (assets_read === assets.length) {
        create_server(assets);
      }
    });
  }
  assets.forEach(open_asset);
}
const parse_order = function(order, assets, res) {
  let burger = [];
  let validated_order = "";
  let ids = assets.map(asset => asset.id);
  [...order].forEach(function (letter) {
    if (ids.indexOf(letter) >= 0) {
      validated_order += letter;
      burger.push(assets[i]);
    }
  });
  
  fs.access(`cache/${filename}.png`, function(does_not_exist){
    if (does_not_exist) {
      create_burger(burger, path, res);
    } else {
      console.log("cache hit");
      diliver_burger(path, res);
    }
  }); 
  // create_burger(burger, validated_order, res);
}

const create_burger = function (burger, validated_order, res) {
  let height = 130 + 25 * (burger.length - 1);
  new Jimp(326, height, (err, canvas) => {
    if (err) {
      throw err;
    }
    let yaxis = 0;
    burger.forEach(function (component) {
      canvas.blit(component.resource, 0, yaxis);
      yaxis += 25;

    });
    canvas
      .flip(false, true)
      .write(`cache/${validated_order}.png`, function () {
        res.writeHead(200, {"Content-Type" : "image/png"});
        console.log("Image Written");

        res.end();
      });
  })
}

function create_server (assets) {
  const new_connection = function (req, res) {
    if (req.url === "/") {
      res.writeHead(200, {"Content-Type" : "text/html"});
      fs
        .createReadStream("./html/index.html")
        .pipe(res);
    } else if (req.url.startsWith("/build")){
      let order = url.parse(req.url, true).query.q;
      parse_order(order, assets, res);
    } else {
      res.writeHead(404);
      res.end();
    }
  }
  const server = http.createServer(new_connection);
  server.listen(port, host);
  console.log(`Now listening on ${host}:${port}`);
}

open_assets();
// Jimp.read('lenna.png', (err, lenna) => {
//   if (err) throw err;
//   lenna
//     .resize(256, 256)
//     .quality(60)
//     .greyscale()
//     .write('lenna-small.jpg');
// });