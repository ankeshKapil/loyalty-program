import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruckersLoyaltyProgramSharedModule } from '../../shared';
import {
    SchemeService,
    SchemePopupService,
    SchemeComponent,
    SchemeDetailComponent,
    SchemeDialogComponent,
    SchemePopupComponent,
    SchemeDeletePopupComponent,
    SchemeDeleteDialogComponent,
    schemeRoute,
    schemePopupRoute,
    SchemeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...schemeRoute,
    ...schemePopupRoute,
];

@NgModule({
    imports: [
        TruckersLoyaltyProgramSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchemeComponent,
        SchemeDetailComponent,
        SchemeDialogComponent,
        SchemeDeleteDialogComponent,
        SchemePopupComponent,
        SchemeDeletePopupComponent,
    ],
    entryComponents: [
        SchemeComponent,
        SchemeDialogComponent,
        SchemePopupComponent,
        SchemeDeleteDialogComponent,
        SchemeDeletePopupComponent,
    ],
    providers: [
        SchemeService,
        SchemePopupService,
        SchemeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckersLoyaltyProgramSchemeModule {}
