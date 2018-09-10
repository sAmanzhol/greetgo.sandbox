import {Component, EventEmitter, Output} from "@angular/core";

@Component({
  selector: 'main-form-component',
  template: `
    <div class="container" >
      <div class="row">
        <div class="col s10"></div>
      <div class="col s2 " >
        <a class="waves-effect waves-light btn material-icons " (click)="exit.emit()" >exit_to_app</a>
      </div>
      
      </div>
    </div>
    <div>
      <client-list></client-list>
    </div>`,
})
export class MainFormComponent {
  @Output() exit = new EventEmitter<void>();

}
