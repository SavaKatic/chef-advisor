import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { ChefadvisorCoreModule } from 'app/core/core.module';
import { ChefadvisorAppRoutingModule } from './app-routing.module';
import { ChefadvisorHomeModule } from './home/home.module';
import { ChefadvisorEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ChefadvisorSharedModule,
    ChefadvisorCoreModule,
    ChefadvisorHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ChefadvisorEntityModule,
    ChefadvisorAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class ChefadvisorAppModule {}
