import {Injectable} from '@angular/core';
import {Feature} from "./feature";
import {DataSource} from "../utils/data-source.util";

@Injectable()
export class FeaturesService {
  private features: Feature[] = [{id: "1", name: "hello"}, {id: "2", name: "features"}];

  constructor(private dataSource: DataSource) {
  }

  getFeatures(): Promise<Feature[]> {

    return this.dataSource.getPromise("features")
      .then(response => response.data as Feature[])
      .catch(this.handleError);

    // return Promise.resolve(this.features);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
