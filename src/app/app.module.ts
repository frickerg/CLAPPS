import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TabsComponent } from '../components/navigation/tabs/tabs.component';
import { HomePage } from '../pages/home/home.page';
import { AboutPage } from '../pages/about/about.page';
import { ContactPage } from '../pages/contact/contact.page';

@NgModule({
	declarations: [
		AppComponent,
		TabsComponent,
		HomePage,
		AboutPage,
		ContactPage
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
