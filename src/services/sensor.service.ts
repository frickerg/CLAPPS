import { Injectable } from '@angular/core';
import { Metawear } from 'metawear';

@Injectable({
	providedIn: 'root'
})
export class SensorService {
	sensor: Metawear;
	availableAddresses: string[];
	availableDevices = [];

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

	public onDiscover(device) {
		this.availableAddresses.forEach((address) => {
			if (device.address.equalsIgnoreCase(address)) {
				console.log('discovered ' + address);
				this.availableDevices.push(device);
			}
		});
		// Complete discovery after finishing scanning for devices
		if (this.availableAddresses.length == this.availableDevices.length) {
			this.sensor.stopDiscoverAll(this.onDiscover);
			setTimeout(function () {
				console.log('discover complete');
				this.availableDevices.forEach(function (device) {
					this.startAccelStream(device);
				});
			}, 1000);
		}
	}

	public discoverAll() {
		this.sensor.discoverAll(this.onDiscover);
	}

	public startAccelStream(device) {
		//TODO
	}

}
