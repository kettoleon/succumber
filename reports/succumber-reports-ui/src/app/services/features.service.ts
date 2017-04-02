import {Injectable} from '@angular/core';
import {Feature} from "./feature";
import {AssetLoader} from "../utils/asset-loader";

@Injectable()
export class FeaturesService {
  private features: Feature[] = [{id: "1", name: "hello"}, {id: "2", name: "features"}];

  constructor(private assetLoader: AssetLoader) {
  }

  getFeatures(): Promise<Feature[]> {

    return this.assetLoader.loadAsset("features")
      .then(response => response.data as Feature[])
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
