import {Injectable} from '@angular/core';
import {EventManager} from "@angular/platform-browser";
import {Http} from "@angular/http";

import 'rxjs/add/operator/toPromise';
import {Observable} from "rxjs";

@Injectable()
export class DataSource {

  private localData: Map<string, any> = new Map();

  constructor(private eventManager: EventManager, private http: Http) {
    const component = this;
    window["setLocalData"] = function (key: string, value: any) {
      window.dispatchEvent(new CustomEvent("localDataLoaded", {detail: {"key": key, "value": value}}));
    }
    eventManager.addGlobalEventListener("window", "localDataLoaded", function (event: CustomEventInit) {
      console.log("custom event received by listener: ", event.detail);
      component.localData.set(event.detail.key, event.detail.value);
    });
    console.log("LocalDataSourceInitialized");
  }

  ngOnDestroy() {
    window["setLocalData"] = null;
  }


  public getPromise(url: string): Promise<any> {
    if (this.isLocal()) {
      return this.loadScript("assets/" + url + ".js").toPromise();
    } else {
      return this.http.get("assets/" + url + ".json").toPromise().then(response => response.json());
    }
  }

  private isLocal(): boolean {
    return location.protocol.startsWith("file");
  }

  private loadScript(scriptSrc: string): Observable<any> {
    var headElement = document.getElementsByTagName("head")[0];
    var linkElement = document.createElement("script");
    const component = this;
    var obs: Observable<any> = new Observable(observer => {
      linkElement.src = scriptSrc;
      linkElement.type = "text/javascript";
      linkElement.onload = function () {
        headElement.removeChild(linkElement);
        observer.next(component.localData.get(scriptSrc));
        observer.complete();
      };

    });
    headElement.appendChild(linkElement);
    return obs;
  }


}
