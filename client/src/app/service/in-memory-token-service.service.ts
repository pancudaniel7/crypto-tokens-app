import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryTokenServiceService {
  constructor() {}

  init() {
    let inMemoryTokenMap = {};
    sessionStorage.setItem(
      'in_memory_token_map',
      JSON.stringify(inMemoryTokenMap)
    );
  }

  update(key: string, value: any) {
    let inMemoryTokenMap = JSON.parse(
      sessionStorage.getItem('in_memory_token_map') || '{}'
    );
    
    
  }
}
