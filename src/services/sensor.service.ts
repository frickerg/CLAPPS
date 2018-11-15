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
		device.connectAndSetUp(function (error) {
			if (error) {
				console.error(error);
				process.exit(1);
			}
			// Set the max range of the accelerometer /TESTING!)
			this.sensor.mbl_mw_acc_set_range(device.board, 8.0);
			this.sensor.mbl_mw_acc_write_acceleration_config(device.board);
			var accSignal = this.sensor.mbl_mw_acc_get_acceleration_data_signal(device.board);
			this.sensor.mbl_mw_datasignal_subscribe(accSignal, null, this.sensor.FnVoid_VoidP_DataP.toPointer(function gotTimer(context, dataPtr) {
				var data = dataPtr.deref();
				var pt = data.parseValue();
				console.log(pt.x, pt.y, pt.z);
			}));
			this.sensor.mbl_mw_acc_enable_acceleration_sampling(device.board);
			this.sensor.mbl_mw_acc_start(device.board);

			// Stop after 5 seconds
			setTimeout(function () {
				// Stop the stream
				this.sensor.mbl_mw_acc_stop(device.board);
				this.sensor.mbl_mw_acc_disable_acceleration_sampling(device.board);
				this.sensor.mbl_mw_datasignal_unsubscribe(accSignal);
				this.sensor.mbl_mw_debug_disconnect(device.board);
			}, 5000);
		});
	}

}
