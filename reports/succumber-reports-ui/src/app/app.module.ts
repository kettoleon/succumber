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
import {DataSource} from "./utils/data-source.util";
import {InMemoryWebApiModule} from "angular-in-memory-web-api";
import {LocalInterceptor} from "./utils/local-interceptor";
import {LocalHttp} from "./utils/local-http";

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
    }),
    InMemoryWebApiModule.forRoot(LocalInterceptor, {passThruUnknownUrl:true})
  ],
  providers: [FeaturesService, DataSource],
  bootstrap: [AppComponent]
})
export class AppModule {
}
