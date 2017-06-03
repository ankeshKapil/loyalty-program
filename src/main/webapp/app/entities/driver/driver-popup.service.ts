import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Driver } from './driver.model';
import { DriverService } from './driver.service';
@Injectable()
export class DriverPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private driverService: DriverService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.driverService.find(id).subscribe((driver) => {
                driver.createdOn = this.datePipe
                    .transform(driver.createdOn, 'yyyy-MM-ddThh:mm');
                driver.updatedOn = this.datePipe
                    .transform(driver.updatedOn, 'yyyy-MM-ddThh:mm');
                this.driverModalRef(component, driver);
            });
        } else {
            return this.driverModalRef(component, new Driver());
        }
    }

    driverModalRef(component: Component, driver: Driver): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.driver = driver;
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
