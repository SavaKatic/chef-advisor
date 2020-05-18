import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientType } from 'app/shared/model/ingredient-type.model';

@Component({
  selector: 'jhi-ingredient-type-detail',
  templateUrl: './ingredient-type-detail.component.html'
})
export class IngredientTypeDetailComponent implements OnInit {
  ingredientType: IIngredientType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientType }) => (this.ingredientType = ingredientType));
  }

  previousState(): void {
    window.history.back();
  }
}
