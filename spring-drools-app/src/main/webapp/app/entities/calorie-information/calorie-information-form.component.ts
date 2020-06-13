import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CalorieInformationService } from './calorie-information.service';
import { ICalorieInformation, CalorieInformation } from 'app/shared/model/calorie-information.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'jhi-calorie-information-form',
  templateUrl: './calorie-information-form.component.html'
})
export class CalorieInformationFormComponent implements OnInit {
  addForm = this.fb.group({
    bodyWeight: [],
    height: [],
    age: [],
    level: [],
    gender: []
  });

  constructor(
    private fb: FormBuilder,
    protected calorieInformationService: CalorieInformationService,
    private router: Router
  ) {

  }
  ngOnInit(): void {

  }

  save(): void {
    const calorieInformation = this.createFromForm();
    this.calorieInformationService.setCaloricIntake(calorieInformation).subscribe((response: any) => {
      this.router.navigate(['']);
    })
  }

  private createFromForm(): ICalorieInformation {
    return {
      ...new CalorieInformation(),
      bodyWeight: this.addForm.get(['bodyWeight'])!.value,
      height: this.addForm.get(['height'])!.value,
      age: this.addForm.get(['age'])!.value,
      level: this.addForm.get(['level'])!.value,
      gender: this.addForm.get(['gender'])!.value
    };
  }
}