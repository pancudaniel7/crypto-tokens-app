import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StartupServiceService {

  constructor() { }

  init() : Promise<Boolean>
  {
    return new Promise<Boolean>((resolve)=>{
        
    });    
  }
}
