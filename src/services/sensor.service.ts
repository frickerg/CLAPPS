import { Injectable } from '@angular/core';
import { Metawear, LedPattern } from 'metawear';

@Injectable({
	providedIn: 'root'
})
export class SensorService {
	sensor: Metawear;

	constructor() {
		this.sensor.discover(device => {
			device.connectAndSetUp(pattern => {
				pattern = new LedPattern();
				this.sensor.mbl_mw_led_load_preset_pattern(pattern.ref(), pattern.LedPreset.BLINK);
				this.sensor.mbl_mw_led_write_pattern(device.board, pattern.ref(), pattern.LedColor.GREEN);
				this.sensor.mbl_mw_led_play(device.board);
			}).catch(error => {
				console.log('second level err');
				console.log(error);
			});
		}).catch(error => {
			console.log('first level err');
			console.log(error);
		});
	}
}
