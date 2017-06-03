import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchemeComponent } from './scheme.component';
import { SchemeDetailComponent } from './scheme-detail.component';
import { SchemePopupComponent } from './scheme-dialog.component';
import { SchemeDeletePopupComponent } from './scheme-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SchemeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const schemeRoute: Routes = [
    {
        path: 'scheme',
        component: SchemeComponent,
        resolve: {
            'pagingParams': SchemeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schemes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'scheme/:id',
        component: SchemeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schemes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schemePopupRoute: Routes = [
    {
        path: 'scheme-new',
        component: SchemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schemes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'scheme/:id/edit',
        component: SchemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schemes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'scheme/:id/delete',
        component: SchemeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schemes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
