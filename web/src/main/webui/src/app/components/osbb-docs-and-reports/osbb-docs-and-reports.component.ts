import { Component, OnInit } from '@angular/core';
import { TranslateService } from 'ng2-translate';
import { ToasterService } from 'angular2-toaster';
import { FileSelectDirective, FileUploader } from 'ng2-file-upload';
import { Subscription } from 'rxjs/Subscription';

import { API_URL } from '../../../assets/models/localhost.config';
import { OsbbDocumentsAndReportsConstants } from './osbb-docs-and-reports.constants';
import { User } from '../../models/user.model';
import { DriveFile } from './google-drive-service/drive-file.model';
import { GoogleDriveService } from './google-drive-service/google-drive.service';
import { LoginService } from '../../shared/login/login.service';

@Component({
  selector: 'docs-and-reports',
  templateUrl: './osbb-docs-and-reports.html',
  styleUrls: ['./osbb-docs-and-reports.scss'],
  providers: [GoogleDriveService, OsbbDocumentsAndReportsConstants],
})
export class OsbbDocumentsAndReportsComponent implements OnInit {

  public uploader: FileUploader;
  public  subscription: Subscription;
  public  currentRole: string;
  public  currentFolder: string;
  public  newFolder: DriveFile;
  public  editable: DriveFile;
  public  deleteId: string;
  public  editMode: boolean;
  public  files: DriveFile[];
  public  parents: string[];
  public  paths: string[];

  constructor(
    public toasterService: ToasterService,
    public translateService: TranslateService,
    public driveService: GoogleDriveService,
    public loginService: LoginService,
    public docsConsts: OsbbDocumentsAndReportsConstants
    ) { }

  public ngOnInit() {
    this.currentRole = this.loginService.getRole();
    this.editMode = false;
    this.clearForm();
    this.editable = new DriveFile();
    this.uploader = new FileUploader({
      url: this.docsConsts.default.uploadUrl + '/' + this.currentFolder,
      authToken: 'Bearer ' + localStorage.getItem('access_token'),
    });
    this.goToRoot();
  }

  public createNewFolder(name: string) {
    if (this.exist(name)) {
      this.toasterService.pop('error', name + ' ' + this.translate('exist'));
    } else {
      this.driveService.createFolder(name, this.currentFolder)
        .subscribe( (data) => {
          this.initFolder(this.currentFolder);
          this.toasterService.pop('success', name + ' ' + this.translate('created'));
        },
        (error) => this.toasterService.pop('error', name + ' ' + this.translate('exist'))
        );
    }
    this.clearForm();
  }

  public clearForm() {
    this.newFolder = new DriveFile();
  }

  public delete() {
    this.driveService.delete(this.deleteId).subscribe(
      (data) => {
        this.initFolder(this.currentFolder);
        this.toasterService.pop('success', this.translate('deleted'));
        this.deleteId = '';
      },
      (error) => this.toasterService.pop('error', 'could_not_delete')
    );
  }

  public update() {
    let name: string = this.editable.name;
    if (this.exist(name)) {
      this.toasterService.pop('error', name + ' ' + this.translate('exist'));
    } else {
      this.driveService.update(this.editable.id, name).subscribe(
        (data) => {
          this.editable = data;
          this.initFolder(this.currentFolder);
        },
        (error) => this.toasterService.pop('error', 'could_not_update')
      );
    }
  }

  public getFile(id: string) {
    this.driveService.getFile(id).subscribe(
      (data) => this.editable = data,
    );
  }

  public toggleEditMode() {
    this.editMode = !this.editMode;
  }

  public exist(name: string): boolean {
    let exist = false;
    this.files.forEach( (file) => {
      if (file.name.toUpperCase() === name.toUpperCase()) { exist = true; };
    });
    return exist;
  }

  public translate(message: string): string {
    let translation: string;
    this.translateService.get(message).subscribe(
      (data) => translation = data
    );
    return translation;
  }

  public setDeleteId(id: string) {
    this.deleteId = id;
  }

  public openFolder(id: string, folderName: string) {
    this.parents.push(this.currentFolder);
    this.currentFolder = id;
    this.initFolder(this.currentFolder);
    this.paths.push(folderName);
  }

  public initFolder(id: string) {
    this.driveService.getFilesByParent(id)
      .subscribe( (data) => {
          this.files = data;
          this.sortFiles();
        });

    this.uploader.setOptions({
      url: this.docsConsts.default.uploadUrl + '/' + this.currentFolder
    });
  }

  public goToRoot() {
    this.parents = [];
    this.paths = [];
    this.currentFolder = this.docsConsts.default.root;
    this.initFolder(this.currentFolder);
  }

  public goUp() {
    if (this.currentFolder !== this.docsConsts.default.root) {
      this.currentFolder = this.parents.pop();
      this.paths.pop();
      this.initFolder(this.currentFolder);
    }
  }

  public goTo(index: number) {
    if (index + 1 === this.parents.length) {
      return;
    }
    this.initFolder(this.parents[index + 1]);
    this.parents = this.parents.slice(0, index + 1);
    this.paths = this.paths.slice(0, index + 1);
  }

  public onUpload() {
    this.uploader.queue.forEach( (item) => {
      if (this.exist(item.file.name)) {
        this.toasterService.pop('error', name + ' ' + this.translate('exist'));
        item.remove();
      } else {
        item.upload();
      }
    });
  }

  public onDownload(id: string, name: string) {
    this.driveService.download(id, name);
  }

  public sortFiles() {
    this.sortByName();
    this.sortFoldersFirst();
  }

  public sortByName() {
    this.files.sort((f1, f2) => {
      let nameA = f1.name.toUpperCase();
      let nameB = f2.name.toUpperCase();
      if (nameA < nameB) {
        return -1;
      }
      if (nameA > nameB) {
        return 1;
      }
      return 0;
    });
  }

  public sortFoldersFirst() {
    this.files.sort((f1, f2) => {
      if (f1.folder && !f2.folder) {
        return -1;
      }
      if (!f1.folder && f2.folder) {
        return 1;
      }
      return 0;
    });
  }

  public clearQueue() {
    this.uploader.clearQueue();
  }

}
