import { Component } from '@angular/core';
import { SensorService } from '../../services/sensor.service';

@Component({
	selector: 'app-sensor',
	templateUrl: 'sensor.page.component.html',
	styleUrls: ['sensor.page.component.scss']
})
export class SensorPageComponent {
	sensor: SensorService;

	constructor(sensor: SensorService) {
		this.sensor = sensor;
	}

	connect() {
		this.sensor.discoverAll();
	}
}


