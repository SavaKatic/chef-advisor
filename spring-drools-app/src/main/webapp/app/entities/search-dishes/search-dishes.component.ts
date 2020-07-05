import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';

import { IDish } from 'app/shared/model/dish.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SearchDishesService } from './search-dishes.service';
import { DishDeleteDialogComponent } from '../dish/dish-delete-dialog.component';

@Component({
  selector: 'jhi-search-dishes',
  templateUrl: './search-dishes.component.html'
})
export class SearchDishesComponent implements OnInit, OnDestroy {
  dishes?: IDish[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  isSearchApplied = false;

  searchForm = this.fb.group({
    strict: [false, [Validators.required]],
    dishType: [''],
    dishCategory: ['']
  })

  constructor(
    protected dishService: SearchDishesService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.dishService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDish[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInDishes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  search() : void {
    this.isSearchApplied = true;
    const queryString = `?isStrict=${this.searchForm.get(['strict'])!.value}&dishType=${this.searchForm.get(['dishType'])!.value}&dishCategory=${this.searchForm.get(['dishCategory'])!.value}`;
    this.dishService.search(queryString).subscribe((data: any) => {
      this.dishes = data.body;
    })
  }

  choose(dish: IDish): void {
    this.dishService.choose(dish).subscribe(() => {
      this.router.navigate(['']);
    })
  }

  trackId(index: number, item: IDish): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDishes(): void {
    this.eventSubscriber = this.eventManager.subscribe('dishListModification', () => this.loadPage());
  }

  delete(dish: IDish): void {
    const modalRef = this.modalService.open(DishDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dish = dish;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDish[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/search-dishes'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.dishes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
