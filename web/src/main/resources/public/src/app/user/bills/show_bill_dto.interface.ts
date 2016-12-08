export interface BillDTO {
    billId: number;
    name: string;
    date: string;
    tariff: number;
    toPay: number;
    paid: number;
    apartmentNumber: number;
    apartmentId:number;
    providerId:number;
    status: string;
    parentBillId: number;
}
