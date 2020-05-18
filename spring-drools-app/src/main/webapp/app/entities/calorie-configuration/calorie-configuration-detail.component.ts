import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

@Component({
  selector: 'jhi-calorie-configuration-detail',
  templateUrl: './calorie-configuration-detail.component.html'
})
export class CalorieConfigurationDetailComponent implements OnInit {
  calorieConfiguration: ICalorieConfiguration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calorieConfiguration }) => (this.calorieConfiguration = calorieConfiguration));
  }

  previousState(): void {
    window.history.back();
  }
}
