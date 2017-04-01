import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, RequestOptionsArgs} from "@angular/http";

@Injectable()
export class LocalHttp{

  constructor(private http: Http) {
  }

  // get(url: string, options?: RequestOptionsArgs): Observable<Response>{
  //   console.log('get...', url);
  //   return this.http.get(url, options).catch(options
  //   );
  // }

}
