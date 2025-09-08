import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Task } from '../../../api/models';
import { InputText } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-task-base-component',
  imports: [CommonModule, FormsModule, InputText, TextareaModule, SelectModule, ButtonModule],
  templateUrl: './task-base-component.html',
  styleUrl: './task-base-component.scss', 
  standalone: true
})
export class TaskBaseComponent {
  @Input() task: Task = {}; 
  @Input() readonly = false; // true = Felder sind disabled
  @Output() save = new EventEmitter<Task>();
  @Output() cancel = new EventEmitter<void>();

  statusOptions = [
    "OFFEN", "IN_BEARBEITUNG", "ERLEDIGT"
  ]; 

  onSave() {
    this.save.emit(this.task);
  }

  onCancel() {
    this.cancel.emit();
  }
}
