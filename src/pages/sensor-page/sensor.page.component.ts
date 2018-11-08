import { Component } from '@angular/core';
import { delay } from 'q';

@Component({
	selector: 'app-sensor',
	templateUrl: 'sensor.page.component.html',
	styleUrls: ['sensor.page.component.scss']
})
export class SensorPageComponent {

	constructor() {

	}

connect() {
	// put some code here

	document.getElementById('explain').textContent = 'Sensor wird verbunden';
	document.getElementById('connectionBtn').hidden = true;
}
}


