var system = require('system');
var page = require('webpage').create();
//console.log('The default user agent is ' + page.settings.userAgent);
page.settings.userAgent = 'SpecialAgent';
page.open(system.args[1], function(status) {
  if (status !== 'success') {
    console.log('Unable to access network');
  } else {
    var ua = page.evaluate(function() {
      return document.getElementById('configStackPage_1').textContent;
    });
    console.log(ua);
  }
  phantom.exit();
});

