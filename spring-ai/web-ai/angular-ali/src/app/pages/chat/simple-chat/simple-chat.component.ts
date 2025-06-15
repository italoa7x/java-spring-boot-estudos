import { NgClass } from '@angular/common';
import { Component, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { catchError, of } from 'rxjs';
import { SimpleChatService } from './simple-chat.service';
interface IChatMessage {
  text: string;
  isBot: boolean;
}

@Component({
  selector: 'app-simple-chat',
  templateUrl: './simple-chat.component.html',
  styleUrl: './simple-chat.component.scss',
  imports: [MatCardModule, MatToolbarModule, MatInputModule, MatButtonModule, MatIconModule, FormsModule, NgClass]
})
export class SimpleChatComponent {
  @ViewChild('chatHistory')
  private chatHistory!: ElementRef;

  inputMessage = '';
  loading = false;

  private chatService = inject(SimpleChatService);



  messages = signal<IChatMessage[]>([])

  sendMessage(): void {
    this.loading = true;
    this.trimMessage();

    if (this.inputMessage !== '') {
      // this.simulateResponse();
      this.sendChatMessage();
    }
  }

  private sendChatMessage(): void {
    this.chatService.sendMessage(this.inputMessage)
      .pipe(
        catchError(() => {
          this.updateMessages('Erro ao enviar mensagem. Tente novamente.', true);
          this.loading = false;
          return of();
        })
      )
      .subscribe({
        next: (response) => {
          console.log('response ', response)
          this.updateMessages(response.message, true);
          this.inputMessage = ''
          this.loading = false;
        },
        error: (error) => {
          console.error('Erro ao enviar mensagem:', error);
          this.loading = false;
        }
      });
  }


  private simulateResponse(): void {
    setTimeout(() => {
      const responseMessage = 'mensagem gerada por IA'
      this.updateMessages(responseMessage, true);
      this.inputMessage = '';
      this.loading = false;
    }, 1000);
  }

  private updateMessages(message: string, isBoot = false): void {
    this.messages.update(messages => [...messages, { text: message, isBot: isBoot }]);
    this.inputMessage = '';
    this.scrollToBottom();
  }

  private trimMessage(): void {
    this.inputMessage = this.inputMessage.trim();
  }

  private scrollToBottom(): void {
    setTimeout(() => {
      if (this.chatHistory) {
        this.chatHistory.nativeElement.scrollTop = this.chatHistory.nativeElement.scrollHeight;
      }
    }, 100);
  }
}
