import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChatResponse } from './model/chat';

@Injectable({
  providedIn: 'root'
})
export class SimpleChatService {
  private readonly baseUrl = '/api/chat';
  private http = inject(HttpClient);

  sendMessage(message: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(this.baseUrl, { message });
  }
}
