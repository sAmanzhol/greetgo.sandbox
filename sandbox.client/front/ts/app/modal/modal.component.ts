import {Component} from "@angular/core";
import {UsersService} from "../users.service";

@Component({
  selector:'app-modal',
  template: require('./modal.component.html'),
  styles: [require('./modal.component.css')],
  providers:[UsersService],
})
export class ModalComponent {


}
