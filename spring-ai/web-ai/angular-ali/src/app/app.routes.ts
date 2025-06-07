import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'simple-chat'
  },
  {
    path: 'simple-chat',
    loadComponent: () => import('./pages/chat/simple-chat/simple-chat.component').then(c => c.SimpleChatComponent)
  },
  {
    path: '**',
    redirectTo: 'simple-chat'
  }
];
