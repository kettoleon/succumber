import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {RouterModule}   from '@angular/router';

import {AppComponent} from './app.component';
import {
  ButtonModule, ChartModule, InputTextModule, MegaMenuModule, SplitButtonModule,
  ToolbarModule
} from "primeng/primeng";
import {MainComponent} from './main/main.component';
import {FeaturesComponent} from './features/features.component';
import {TagsComponent} from './tags/tags.component';
import {StatsComponent} from './stats/stats.component';
import {FeaturesService} from "./services/features.service";
import {AssetLoader} from "./utils/asset-loader";

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    FeaturesComponent,
    TagsComponent,
    StatsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule, InputTextModule, ButtonModule, MegaMenuModule, ToolbarModule, SplitButtonModule, ChartModule,
    RouterModule.forRoot([
      {
        path: "",
        component: MainComponent
      },
      {
        path: "features",
        component: FeaturesComponent
      },
      {
        path: "stats",
        component: StatsComponent
      },
      {
        path: "tags",
        component: TagsComponent
      }

    ], {
      useHash: location.protocol.startsWith("file")
    })
  ],
  providers: [FeaturesService, AssetLoader],
  bootstrap: [AppComponent]
})
export class AppModule {
}
