import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

@Component({
  selector: 'jhi-ingredient-model-detail',
  templateUrl: './ingredient-model-detail.component.html'
})
export class IngredientModelDetailComponent implements OnInit {
  ingredientModel: IIngredientModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientModel }) => (this.ingredientModel = ingredientModel));
  }

  previousState(): void {
    window.history.back();
  }
}
