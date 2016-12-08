export interface BillDTO {
    billId: number;
    name:string;
    date: string;
    tariff: number;
    toPay: number;
    paid: number;
    parentBillId:number;
}
