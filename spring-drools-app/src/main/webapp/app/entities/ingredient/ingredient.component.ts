import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIngredient } from 'app/shared/model/ingredient.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IngredientService } from './ingredient.service';
import { IngredientDeleteDialogComponent } from './ingredient-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-ingredient',
  templateUrl: './ingredient.component.html'
})
export class IngredientComponent implements OnInit, OnDestroy {
  ingredients?: IIngredient[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected ingredientService: IngredientService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected accountService: AccountService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.ingredientService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIngredient[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      if (this.accountService.hasAnyAuthority('ROLE_USER')) {
       // eslint-disable-next-line no-console
        console.log(data);
        // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
        // @ts-ignore
        this.ingredients = data.ingredients;
        return;
      }
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInIngredients();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIngredient): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIngredients(): void {
    this.eventSubscriber = this.eventManager.subscribe('ingredientListModification', () => this.loadPage());
  }

  delete(ingredient: IIngredient): void {
    const modalRef = this.modalService.open(IngredientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ingredient = ingredient;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIngredient[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    // this.router.navigate(['/ingredient'], {
    //   queryParams: {
    //     page: this.page,
    //     size: this.itemsPerPage,
    //     sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
    //   }
    // });
    // this.ingredients = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
