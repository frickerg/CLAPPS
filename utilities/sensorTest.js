var MetaWear = require('metawear');

console.log('starting metawear');
MetaWear.discover(function (device) {
	device.connectAndSetUp(function (error) {
		var pattern = new MetaWear.LedPattern();
		MetaWear.mbl_mw_led_load_preset_pattern(pattern.ref(), MetaWear.LedPreset.BLINK);
		MetaWear.mbl_mw_led_write_pattern(device.board, pattern.ref(), MetaWear.LedColor.GREEN);
		MetaWear.mbl_mw_led_play(device.board);
		// After 5 seconds we reset the board to clear the LED, when we receive
		// a disconnect notice we know the reset is complete, so exit the program
		setTimeout(function () {
			device.on('disconnect', function () {
				process.exit(0);
			});
			MetaWear.mbl_mw_debug_reset(device.board);
		}, 5000);
	});
});
