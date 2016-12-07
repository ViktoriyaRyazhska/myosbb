// import { Component, OnInit } from '@angular/core';
// import { ROUTER_DIRECTIVES } from "@angular/router";
// import { TranslatePipe } from 'ng2-translate';

// import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
// import { User } from '../../../shared/models/User';
// import { CurrentUserService } from "../../../shared/services/current.user.service";
// import { FolderService } from './folder-manager/folder.service';
// import { Folder } from './folder-manager/Folder';

// @Component({
//     selector: 'osbb-docs-and-reports',
//     templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
//     // templateUrl: 'src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.html',
//     styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css',
//                 'src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.css'],
//     directives: [ROUTER_DIRECTIVES],
//     providers: [FolderService],
//     pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
// })
// export class OsbbDocumentsAndReportsComponent1 implements OnInit {
//     private currentRole: string;
//     private folders: Folder[];
//     private newFolder: Folder = new Folder(); 

//     constructor(private userService: CurrentUserService,                
//                 private folderService: FolderService)  { }

//     ngOnInit() {
//         console.log('Initializing OSBB Docs and Reports...');
//         this.currentRole = this.userService.getRole();
//         this.folderService.getFoldersByParentId(1)
//             .subscribe(
//                 data => this.folders = data,
//                 error => console.log(error)
//             );
//     }

//     createNewFolder(name: string) {
//         console.log(this.newFolder.name);
//         this.folderService.saveFolder(name, 1)
//             .subscribe(
//                 data => {
//                     this.newFolder = data;
//                     console.log(this.newFolder.id)
//                 },
//                 error => console.log(error)
//             );
//     }
// }
