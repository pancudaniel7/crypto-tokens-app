import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root',
})
export class InMemoryTokenService {
  constructor() {}

  get(): any {
    return JSON.parse(sessionStorage.getItem('in_memory_token_map') || '{}');
  }

  set(inMemoryTokenMap: any) {
    sessionStorage.setItem(
      'in_memory_token_map',
      JSON.stringify(inMemoryTokenMap)
    );
  }

  init() {
    let inMemoryTokenMap = {};
    this.set(inMemoryTokenMap);
  }

  update(key: string, value: any) {
    let inMemoryTokenMap = this.get();

    inMemoryTokenMap[key] = value;
    this.set(inMemoryTokenMap);
  }
}
