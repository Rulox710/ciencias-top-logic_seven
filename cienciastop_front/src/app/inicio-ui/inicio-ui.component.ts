import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inicio-ui',
  templateUrl: './inicio-ui.component.html',
  styleUrls: ['./inicio-ui.component.css']
})
export class InicioUiComponent implements OnInit {

  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash"

  constructor() { }

  ngOnInit(): void {
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-eye-slash";
    this.isText ? this.type = "text" : this.type = "password";
  }

}
