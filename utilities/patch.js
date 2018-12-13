const fs = require('fs');
const angular = 'node_modules/@angular-devkit/build-angular/src/angular-cli-files/models/webpack-configs/browser.js';
const noble_ws = 'node_modules/metawear/node_modules/noble/lib/websocket/bindings.js';

patch(angular, 'node: false', 'node: { crypto: true, stream: true, buffer: true, fs: "empty", net: "empty", tls: "empty", ws: "empty" }');
patch(noble_ws, 'require(\'ws\')', 'require(\'isomorphic-ws\')');

function patch(path, source, replace) {
	fs.readFile(path, 'utf8', function (err, data) {
		if (err) {
			console.log('\x1b[31m%s\x1b[0m', path + ' has not been served yet');
			console.log('\x1b[33m%s\x1b[0m', 'run the patch again after ionic serve!\n');
			return;
		}

		var result = data.replace(source, replace);

		if (data.includes(replace)) {
			return console.log('no action needed on ' + path);
		}
		fs.writeFile(path, result, 'utf8', function (err) {
			if (err) return console.err(err);
			console.log('\x1b[32m%s\x1b[0m', 'patch applied to ' + path);
		});
	});
}
