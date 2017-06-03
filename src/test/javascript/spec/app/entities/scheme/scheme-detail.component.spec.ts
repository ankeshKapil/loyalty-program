import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { TruckersLoyaltyProgramTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SchemeDetailComponent } from '../../../../../../main/webapp/app/entities/scheme/scheme-detail.component';
import { SchemeService } from '../../../../../../main/webapp/app/entities/scheme/scheme.service';
import { Scheme } from '../../../../../../main/webapp/app/entities/scheme/scheme.model';

describe('Component Tests', () => {

    describe('Scheme Management Detail Component', () => {
        let comp: SchemeDetailComponent;
        let fixture: ComponentFixture<SchemeDetailComponent>;
        let service: SchemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TruckersLoyaltyProgramTestModule],
                declarations: [SchemeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SchemeService,
                    EventManager
                ]
            }).overrideComponent(SchemeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchemeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchemeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Scheme(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.scheme).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
