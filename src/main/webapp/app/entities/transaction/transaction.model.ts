export class Transaction {
    constructor(
        public id?: number,
        public time?: any,
        public fuelVolume?: number,
        public amount?: number,
        public driverId?: number,
    ) {
    }
}
