import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Driver } from './driver.model';
import { DriverPopupService } from './driver-popup.service';
import { DriverService } from './driver.service';

@Component({
    selector: 'jhi-driver-dialog',
    templateUrl: './driver-dialog.component.html'
})
export class DriverDialogComponent implements OnInit {

    driver: Driver;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private driverService: DriverService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.driver.id !== undefined) {
            this.subscribeToSaveResponse(
                this.driverService.update(this.driver));
        } else {
            this.subscribeToSaveResponse(
                this.driverService.create(this.driver));
        }
    }

    private subscribeToSaveResponse(result: Observable<Driver>) {
        result.subscribe((res: Driver) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Driver) {
        this.eventManager.broadcast({ name: 'driverListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-driver-popup',
    template: ''
})
export class DriverPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private driverPopupService: DriverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.driverPopupService
                    .open(DriverDialogComponent, params['id']);
            } else {
                this.modalRef = this.driverPopupService
                    .open(DriverDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
