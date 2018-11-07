import { Component } from '@angular/core';
import { NgModule } from '@angular/core';
import {RouterModule} from '@angular/router';


@Component({
	selector: 'app-tip',
	templateUrl: 'tip.page.component.html',
	styleUrls: ['tip.page.component.scss']
})

@NgModule({
	imports: [
		RouterModule.forChild([
			{ path: '', component: TipPageComponent },
			{ path: 'tipDetail', loadChildren: '../tipDetail/tipDetail.page.component#TipDetailPageComponent' },
		])
	],
	declarations: [TipPageComponent]
})
export class TipPageComponent { }
