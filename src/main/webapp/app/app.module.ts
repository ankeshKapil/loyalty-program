import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { TruckersLoyaltyProgramSharedModule, UserRouteAccessService } from './shared';
import { TruckersLoyaltyProgramHomeModule } from './home/home.module';
import { TruckersLoyaltyProgramAdminModule } from './admin/admin.module';
import { TruckersLoyaltyProgramAccountModule } from './account/account.module';
import { TruckersLoyaltyProgramEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        TruckersLoyaltyProgramSharedModule,
        TruckersLoyaltyProgramHomeModule,
        TruckersLoyaltyProgramAdminModule,
        TruckersLoyaltyProgramAccountModule,
        TruckersLoyaltyProgramEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class TruckersLoyaltyProgramAppModule {}
