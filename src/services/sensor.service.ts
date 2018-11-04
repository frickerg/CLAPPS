import { Injectable } from '@angular/core';
import { Metawear } from 'metawear';

@Injectable({
	providedIn: 'root'
})
export class SensorService {
	sensor: Metawear;

	constructor() {
		this.sensor.discover(device => {
			device.connectAndSetUp(pattern => {
				pattern = new this.sensor.LedPattern();
				this.sensor.mbl_mw_led_load_preset_pattern(pattern.ref(), this.sensor.LedPreset.BLINK);
				this.sensor.mbl_mw_led_write_pattern(device.board, pattern.ref(), this.sensor.LedColor.GREEN);
				this.sensor.mbl_mw_led_play(device.board);

				// After 5 seconds we reset the board to clear the LED, when we receive
				// a disconnect notice we know the reset is complete
				setTimeout(function () {
					device.on('disconnect', function () {
						process.exit(0);
					});
					this.sensor.mbl_mw_debug_reset(device.board);
				}, 5000);
			}).catch(error => {
				// TODO: Remove this after successful run
				console.log('second level err');
				console.log(error);
			});
		}).catch(error => {
			// TODO: Remove this after successful run
			console.log('first level err');
			console.log(error);
		});
	}
}
