import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Scheme } from './scheme.model';
import { SchemeService } from './scheme.service';

@Component({
    selector: 'jhi-scheme-detail',
    templateUrl: './scheme-detail.component.html'
})
export class SchemeDetailComponent implements OnInit, OnDestroy {

    scheme: Scheme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private schemeService: SchemeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSchemes();
    }

    load(id) {
        this.schemeService.find(id).subscribe((scheme) => {
            this.scheme = scheme;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSchemes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'schemeListModification',
            (response) => this.load(this.scheme.id)
        );
    }
}
