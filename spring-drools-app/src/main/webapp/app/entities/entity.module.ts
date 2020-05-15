import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ingredient',
        loadChildren: () => import('./ingredient/ingredient.module').then(m => m.ChefadvisorIngredientModule)
      },
      {
        path: 'dish-type',
        loadChildren: () => import('./dish-type/dish-type.module').then(m => m.ChefadvisorDishTypeModule)
      },
      {
        path: 'ingredient-type',
        loadChildren: () => import('./ingredient-type/ingredient-type.module').then(m => m.ChefadvisorIngredientTypeModule)
      },
      {
        path: 'unit-type',
        loadChildren: () => import('./unit-type/unit-type.module').then(m => m.ChefadvisorUnitTypeModule)
      },
      {
        path: 'ingredient-model',
        loadChildren: () => import('./ingredient-model/ingredient-model.module').then(m => m.ChefadvisorIngredientModelModule)
      },
      {
        path: 'dish',
        loadChildren: () => import('./dish/dish.module').then(m => m.ChefadvisorDishModule)
      },
      {
        path: 'calorie-configuration',
        loadChildren: () =>
          import('./calorie-configuration/calorie-configuration.module').then(m => m.ChefadvisorCalorieConfigurationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ChefadvisorEntityModule {}
