## Tests prove the problem

## How to reproduce the problem in browser

- In a browser (I used Chrome v98), open http://localhost:8080/real/event once. 
- Then open console and paste the script below - this open EventSource to /real/events
```
{
  const sse = new EventSource('/real/events');
  sse.onerror = (e) => {
     console.log('Connection closed');
     sse.close();
  }
  
  sse.addEventListener('TYPE1', e => console.log(e.data));
}
```
Everything works. Logs appear in console and if you look at Network tab, EventStream lists received events correctly.

- Next try the same for http://localhost:8080/real/event once. Then open console and paste the script below
```
{
  const sse = new EventSource('/proxy/events');
  sse.onerror = (e) => {
     console.log('Connection closed');
     sse.close();
  }
  
  sse.addEventListener('TYPE1', e => console.log(e.data));
}
```
No logs and Network tab also does not list events.

- Now hit the proxy url from browser address bar http://localhost:8080/proxy/events 
Events steam to UI as and when they are received which proves that event is streamed correctly via proxy filter but browser EventSource does not raise listener call back event ONLY WHEN call is proxed.


## HELP!!
