const form = function(title, user_input, search_button) {
  let old_search = user_input || "";
  return `<form action="search" method="get">
            <fieldset>
              <legend>${title}</legend>
              <input id="q" name="q" type="text" value="${old_search}"/>
              <input type="submit" value="${search_button || "Search"}" />
              <a target="_blank" href="https://myaccount.google.com/permissions">Revoke Drive Access</a>
            </fieldset>
          </form>`;
}

const html_wrap = function(title, body) {
  return  `<!DOCTYPE html>
           <html>
            <head>
              <title>${title}</title>
              <style>*{font-size:18pt;}</style>
            </head>
            <body onload="flash_timeout()">
              ${body}
            </body>
            </html>`;
}

const flash = function(message) {
  return `<p id="flash" style="font-size:0.7em; color:red">${message}</p>`;
}

const item_wrap = function(title, detail, img_link, flash) {
  return `<h2>${title}</h2>
          <div>
            <img src="${img_link}" style="height: auto; width: 50%;"/>
            <p style="font-size:0.9em">${detail}</p>

          </div>`
}

const fault_page = function(value, message) {
  return html_wrap("NASA Search", form("Astronomy Picture of the Day", value, "Search Date")) 
                                     + 
                                     flash(message);
}

exports.form = form;
exports.html_wrap = html_wrap;
exports.item_wrap = item_wrap;
exports.flash = flash;
exports.fault_page = fault_page;