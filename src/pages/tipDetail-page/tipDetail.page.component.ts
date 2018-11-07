import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-tipDetail',
	templateUrl: 'tipDetail.page.component.html',
	// styleUrls: ['tip.page.component.scss']
})
export class TipDetailPageComponent {
	private todo;

	constructor(private route: ActivatedRoute) {

	}
}

