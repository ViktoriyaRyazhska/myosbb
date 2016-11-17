import { IOsbb, Osbb } from "../../../../shared/models/osbb";
import { FileUploader } from "ng2-file-upload/ng2-file-upload";

export class OsbbDTO {
    osbb:IOsbb;
    file: any;
    isChanged: boolean;
}