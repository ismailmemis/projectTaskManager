import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Project } from '../../../api/models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-project-base',
  imports: [CommonModule, FormsModule, InputText, TextareaModule, ButtonModule],
  templateUrl: './project-base-component.html',
  styleUrl: './project-base-component.scss', 
  standalone: true
})
export class ProjectBaseComponent {
  @Input() project: Project = {};
  @Input() readOnly = false;   // true = Felder sind disabled
  @Output() save = new EventEmitter<Project>();
  @Output() cancel = new EventEmitter<void>();

  onSave() {
    this.save.emit(this.project);
  }

  onCancel() {
    this.cancel.emit();
  }
}
