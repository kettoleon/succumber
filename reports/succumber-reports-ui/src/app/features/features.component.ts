import {Component, OnInit} from '@angular/core';
import {FeaturesService} from '../services/features.service';
import {Feature} from "../services/feature";

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent implements OnInit {

  private features: Feature[];

  constructor(private featuresService: FeaturesService) {
  }

  ngOnInit() {
    this.featuresService.getFeatures().then(features => this.features = features);
  }

}
