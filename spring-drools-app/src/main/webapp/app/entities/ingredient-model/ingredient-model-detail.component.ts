import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

@Component({
  selector: 'jhi-ingredient-model-detail',
  templateUrl: './ingredient-model-detail.component.html'
})
export class IngredientModelDetailComponent implements OnInit {
  ingredientModel: IIngredientModel | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientModel }) => (this.ingredientModel = ingredientModel));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
