import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Scheme } from './scheme.model';
import { SchemeService } from './scheme.service';
@Injectable()
export class SchemePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schemeService: SchemeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schemeService.find(id).subscribe((scheme) => {
                if (scheme.startDate) {
                    scheme.startDate = {
                        year: scheme.startDate.getFullYear(),
                        month: scheme.startDate.getMonth() + 1,
                        day: scheme.startDate.getDate()
                    };
                }
                if (scheme.endDate) {
                    scheme.endDate = {
                        year: scheme.endDate.getFullYear(),
                        month: scheme.endDate.getMonth() + 1,
                        day: scheme.endDate.getDate()
                    };
                }
                this.schemeModalRef(component, scheme);
            });
        } else {
            return this.schemeModalRef(component, new Scheme());
        }
    }

    schemeModalRef(component: Component, scheme: Scheme): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.scheme = scheme;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
