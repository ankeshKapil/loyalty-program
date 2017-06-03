import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TruckersLoyaltyProgramDriverModule } from './driver/driver.module';
import { TruckersLoyaltyProgramTransactionModule } from './transaction/transaction.module';
import { TruckersLoyaltyProgramSchemeModule } from './scheme/scheme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TruckersLoyaltyProgramDriverModule,
        TruckersLoyaltyProgramTransactionModule,
        TruckersLoyaltyProgramSchemeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruckersLoyaltyProgramEntityModule {}
