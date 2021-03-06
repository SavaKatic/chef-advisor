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
        path: 'calorie-information',
        loadChildren: () => import('./calorie-information/calorie-information.module').then(m => m.ChefadvisorCalorieInformationModule)
      },
      {
        path: 'search-dishes',
        loadChildren: () => import('./search-dishes/search-dishes.module').then(m => m.ChefadvisorSearchDishesModule)
      },
      {
        path: 'my-dishes',
        loadChildren: () => import('./my-dishes/my-dishes.module').then(m => m.ChefadvisorMyDishesModule)
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
      },
      {
        path: 'rating',
        loadChildren: () => import('./rating/rating.module').then(m => m.ChefadvisorRatingModule)
      },
      {
        path: 'alarm-trigger-template',
        loadChildren: () =>
          import('./alarm-trigger-template/alarm-trigger-template.module').then(m => m.ChefadvisorAlarmTriggerTemplateModule)
      },
      {
        path: 'spam-detection-template',
        loadChildren: () =>
          import('./spam-detection-template/spam-detection-template.module').then(m => m.ChefadvisorSpamDetectionTemplateModule)
      },
      {
        path: 'alarm',
        loadChildren: () => import('./alarm/alarm.module').then(m => m.ChefadvisorAlarmModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ChefadvisorEntityModule {}
