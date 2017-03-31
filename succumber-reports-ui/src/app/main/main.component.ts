import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  data: any;
  options: any;

  constructor() {
    this.data = {
      labels: [
        "Passed",
        "Failed",
        "Skipped",
        "Pending",
        "Undefined"
      ],
      datasets: [
        {
          data: [90, 5, 3, 1, 1],
          backgroundColor: [
            "#66DD66",
            "#FF8888",
            "#4488FF",
            "#FFFF88",
            "#FF8844"
          ],
          hoverBackgroundColor: [
            "#44BB44",
            "#DD6666",
            "#2266DD",
            "#DDDD66",
            "#DD6622"
          ]
        }]
    };
    this.options = {
      animation: {
        animateScale: true
      },
      circumference: Math.PI,
      rotation: 3 * Math.PI
    };
  }

  ngOnInit() {
  }

}
