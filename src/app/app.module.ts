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
import { DiaryPageComponent } from '../pages/diary-page/diary.page.component';
import { TipPageComponent } from '../pages/tip-page/tip.page.component';
import { SensorPageComponent } from '../pages/sensor-page/sensor.page.component';

@NgModule({
	declarations: [
		AppComponent,
		TabsComponent,
		HomePageComponent,
		DiaryPageComponent,
		TipPageComponent,
		SensorPageComponent
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
