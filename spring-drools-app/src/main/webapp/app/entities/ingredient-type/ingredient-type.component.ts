import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIngredientType } from 'app/shared/model/ingredient-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IngredientTypeService } from './ingredient-type.service';
import { IngredientTypeDeleteDialogComponent } from './ingredient-type-delete-dialog.component';

@Component({
  selector: 'jhi-ingredient-type',
  templateUrl: './ingredient-type.component.html'
})
export class IngredientTypeComponent implements OnInit, OnDestroy {
  ingredientTypes: IIngredientType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ingredientTypeService: IngredientTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ingredientTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ingredientTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IIngredientType[]>) => this.paginateIngredientTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ingredientTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIngredientTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIngredientType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIngredientTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('ingredientTypeListModification', () => this.reset());
  }

  delete(ingredientType: IIngredientType): void {
    const modalRef = this.modalService.open(IngredientTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ingredientType = ingredientType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateIngredientTypes(data: IIngredientType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ingredientTypes.push(data[i]);
      }
    }
  }
}
