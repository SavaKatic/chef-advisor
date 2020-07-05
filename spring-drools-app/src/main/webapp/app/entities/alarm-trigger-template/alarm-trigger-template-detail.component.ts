import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

@Component({
  selector: 'jhi-alarm-trigger-template-detail',
  templateUrl: './alarm-trigger-template-detail.component.html'
})
export class AlarmTriggerTemplateDetailComponent implements OnInit {
  alarmTriggerTemplate: IAlarmTriggerTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarmTriggerTemplate }) => (this.alarmTriggerTemplate = alarmTriggerTemplate));
  }

  previousState(): void {
    window.history.back();
  }
}
