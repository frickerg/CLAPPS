import { Injectable } from '@angular/core';
import { Metawear } from 'metawear';

@Injectable({
	providedIn: 'root'
})
export class SensorService {
	sensor: Metawear;
	availableDevices: Metawear[];

	constructor() {
	}

	public discoverDevice(): Metawear {
		this.availableDevices = [];
		return this.sensor.discover(device => {
			console.log('discovered ' + device.address);
			this.sensor.push(device);
		});
	}

	public connectDeviceByAddress(address: string): Metawear {
		this.sensor.discoverByAddress(address, device => {
			console.log('discovered ' + device.address);
			device.connectAndSetUp(device => {
				console.log('connected ' + device.address);
			}, error => {
				console.log(error);
			});
		});
	}

	public logFromDevice(address: string): Metawear {
		//TODO
	}
}
