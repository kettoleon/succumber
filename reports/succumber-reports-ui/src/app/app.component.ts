import {Component} from '@angular/core';
import {MenuItem} from "primeng/components/common/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  name: string;

  items: MenuItem[];

  message: string;

  onClick() {
    this.message = 'Searching... ' + this.name;
  }

  ngOnInit() {
    this.items = [
      {
        label: 'Succumber Reports', icon: 'fa-check',
      },
      {
        label: 'Back to Jenkins', icon: 'fa-soccer-ball-o',
      }
    ];

  }

}
