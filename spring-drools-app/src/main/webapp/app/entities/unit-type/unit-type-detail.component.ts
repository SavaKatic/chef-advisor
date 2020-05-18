import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitType } from 'app/shared/model/unit-type.model';

@Component({
  selector: 'jhi-unit-type-detail',
  templateUrl: './unit-type-detail.component.html'
})
export class UnitTypeDetailComponent implements OnInit {
  unitType: IUnitType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitType }) => (this.unitType = unitType));
  }

  previousState(): void {
    window.history.back();
  }
}
