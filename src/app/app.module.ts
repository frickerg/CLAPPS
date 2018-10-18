import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TabsComponent } from '../components/navigation/tabs/tabs.component';
import { HomePageComponent } from '../pages/home-page/home.page.component';
import { AboutPageComponent } from '../pages/about-page/about.page.component';
import { ContactPageComponent } from '../pages/contact-page/contact.page.component';

@NgModule({
	declarations: [
		AppComponent,
		TabsComponent,
		HomePageComponent,
		AboutPageComponent,
		ContactPageComponent
	],
	entryComponents: [],
	imports: [
		BrowserModule,
		IonicModule.forRoot(),
		AppRoutingModule
	],
	providers: [
		StatusBar,
		SplashScreen,
		{
			provide: RouteReuseStrategy,
			useClass: IonicRouteStrategy
		}
	],
	bootstrap: [
		AppComponent
	]
})
export class AppModule { }
