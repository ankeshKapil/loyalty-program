import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Driver } from './driver.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DriverService {

    private resourceUrl = 'api/drivers';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(driver: Driver): Observable<Driver> {
        const copy = this.convert(driver);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(driver: Driver): Observable<Driver> {
        const copy = this.convert(driver);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Driver> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.createdOn = this.dateUtils
            .convertDateTimeFromServer(entity.createdOn);
        entity.updatedOn = this.dateUtils
            .convertDateTimeFromServer(entity.updatedOn);
    }

    private convert(driver: Driver): Driver {
        const copy: Driver = Object.assign({}, driver);
        // will update at server side with server date
        copy.createdOn = this.dateUtils.toDate(driver.createdOn);
        copy.updatedOn = this.dateUtils.toDate(driver.updatedOn);
        return copy;
    }
}
