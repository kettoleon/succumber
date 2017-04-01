import {InMemoryDbService, ParsedUrl} from 'angular-in-memory-web-api';
import {QueryEncoder} from "@angular/http";

export class LocalInterceptor implements InMemoryDbService {

  createDb() {
    let heroes = [
      { id: '1', name: 'Windstorm' },
      { id: '2', name: 'Bombasto' },
      { id: '3', name: 'Magneta' },
      { id: '4', name: 'Tornado' }
    ];
    return {heroes};
  }

}
