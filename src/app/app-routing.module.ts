import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsComponent } from '../components/navigation/tabs/tabs.component';
import { HomePageComponent } from '../pages/home-page/home.page.component';
import { DiaryPageComponent } from '../pages/diary-page/diary.page.component';
import { TipPageComponent } from '../pages/tip-page/tip.page.component';
import { SensorPageComponent } from '../pages/sensor-page/sensor.page.component';

const routes: Routes = [
	{
		path: 'tabs',
		component: TabsComponent,
		children: [
			{
				path: '',
				redirectTo: '/tabs/(home:home)',
				pathMatch: 'full',
			},
			{
				path: 'home',
				outlet: 'home',
				component: HomePageComponent
			},
			{
				path: 'diary',
				outlet: 'diary',
				component: DiaryPageComponent
			},
			{
				path: 'tip',
				outlet: 'tip',
				component: TipPageComponent
			},
			{
				path: 'sensor',
				outlet: 'sensor',
				component: SensorPageComponent
			}
		]
	},
	{
		path: '',
		redirectTo: '/tabs/(home:home)',
		pathMatch: 'full'
	},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
