import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsComponent } from '../components/navigation/tabs/tabs.component';
import { HomePage } from '../pages/home/home.page';
import { AboutPage } from '../pages/about/about.page';
import { ContactPage } from '../pages/contact/contact.page';

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
				component: HomePage
			},
			{
				path: 'about',
				outlet: 'about',
				component: AboutPage
			},
			{
				path: 'contact',
				outlet: 'contact',
				component: ContactPage
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
