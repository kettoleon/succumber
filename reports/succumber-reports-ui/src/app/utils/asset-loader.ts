import {Injectable} from '@angular/core';
import {Http} from "@angular/http";

import 'rxjs/add/operator/toPromise';
import {Observable, Subscriber} from "rxjs";

@Injectable()
export class AssetLoader {

  private localAssets: Map<string, AssetDefinition> = new Map();

  private headElement: HTMLHeadElement = document.getElementsByTagName("head")[0];

  constructor(private http: Http) {
    const component = this;
    window["declareAsset"] = function (assetDefinition: AssetDefinition) {
      component.localAssets.set(assetDefinition.asset, assetDefinition);
    };
  }

  ngOnDestroy() {
    window["declareAsset"] = null;
  }

  public loadAsset(asset: string): Promise<any> {
    if (AssetLoader.isLocal()) {
      return this.loadLocalAsset(asset);
    } else {
      return this.loadServerAsset(asset);
    }
  }

  private static isLocal(): boolean {
    return location.protocol.startsWith("file");
  }

  private loadServerAsset(asset: string): Promise<any> {
    return this.http.get("assets/" + asset + ".json").toPromise().then(response => response.json());
  }

  private loadLocalAsset(asset: string): Promise<any> {
    const component = this;

    let subscriber: Subscriber<any>;
    let observer: Observable<any> = new Observable(obs => {
      subscriber = obs;
    });

    let scriptElement: HTMLScriptElement = document.createElement("script");
    scriptElement.src = "assets/" + asset + ".jsonp";
    scriptElement.type = "text/javascript";
    scriptElement.onload = function () {
      subscriber.next(component.getLoadedAssetAndCleanUp(asset, scriptElement));
      subscriber.complete();
    };

    this.headElement.appendChild(scriptElement);
    return observer.toPromise();
  }

  private getLoadedAssetAndCleanUp(asset: string, linkElement: HTMLScriptElement): AssetDefinition {
    let localAsset: AssetDefinition = this.localAssets.get(asset);
    this.localAssets.delete(asset);
    this.headElement.removeChild(linkElement);
    return localAsset;
  }


}
export class AssetDefinition {
  asset: string;
  data: any;
}
