import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Scheme } from './scheme.model';
import { SchemePopupService } from './scheme-popup.service';
import { SchemeService } from './scheme.service';
import { Driver, DriverService } from '../driver';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-scheme-dialog',
    templateUrl: './scheme-dialog.component.html'
})
export class SchemeDialogComponent implements OnInit {

    scheme: Scheme;
    authorities: any[];
    isSaving: boolean;

    drivers: Driver[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private schemeService: SchemeService,
        private driverService: DriverService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.driverService
            .query({filter: 'scheme-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.scheme.driverId) {
                    this.drivers = res.json;
                } else {
                    this.driverService
                        .find(this.scheme.driverId)
                        .subscribe((subRes: Driver) => {
                            this.drivers = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.scheme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schemeService.update(this.scheme));
        } else {
            this.subscribeToSaveResponse(
                this.schemeService.create(this.scheme));
        }
    }

    private subscribeToSaveResponse(result: Observable<Scheme>) {
        result.subscribe((res: Scheme) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Scheme) {
        this.eventManager.broadcast({ name: 'schemeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackDriverById(index: number, item: Driver) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-scheme-popup',
    template: ''
})
export class SchemePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schemePopupService: SchemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.schemePopupService
                    .open(SchemeDialogComponent, params['id']);
            } else {
                this.modalRef = this.schemePopupService
                    .open(SchemeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
