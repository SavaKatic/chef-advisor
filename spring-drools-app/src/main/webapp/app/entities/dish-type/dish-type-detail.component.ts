import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDishType } from 'app/shared/model/dish-type.model';

@Component({
  selector: 'jhi-dish-type-detail',
  templateUrl: './dish-type-detail.component.html'
})
export class DishTypeDetailComponent implements OnInit {
  dishType: IDishType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dishType }) => (this.dishType = dishType));
  }

  previousState(): void {
    window.history.back();
  }
}
