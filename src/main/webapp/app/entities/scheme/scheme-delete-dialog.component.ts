import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Scheme } from './scheme.model';
import { SchemePopupService } from './scheme-popup.service';
import { SchemeService } from './scheme.service';

@Component({
    selector: 'jhi-scheme-delete-dialog',
    templateUrl: './scheme-delete-dialog.component.html'
})
export class SchemeDeleteDialogComponent {

    scheme: Scheme;

    constructor(
        private schemeService: SchemeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schemeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'schemeListModification',
                content: 'Deleted an scheme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-scheme-delete-popup',
    template: ''
})
export class SchemeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schemePopupService: SchemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.schemePopupService
                .open(SchemeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
