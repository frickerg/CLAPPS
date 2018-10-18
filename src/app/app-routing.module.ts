import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsComponent } from '../components/navigation/tabs/tabs.component';
import { HomePageComponent } from '../pages/home-page/home.page.component';
import { AboutPageComponent } from '../pages/about-page/about.page.component';
import { ContactPageComponent } from '../pages/contact-page/contact.page.component';

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
				path: 'about',
				outlet: 'about',
				component: AboutPageComponent
			},
			{
				path: 'contact',
				outlet: 'contact',
				component: ContactPageComponent
			}
		]
	},
	{
		path: '',
		redirectTo: '/tabs/(home:home)',
		pathMatch: 'full'
	}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
