# CN_Assignment1
COMP 6461
Curl-like-application: cURL-like Command-Line Implementation


A simple HTTP Client application (curl command line implementation) which is connected to servers and get the Response from Server and printing to console or written to the specific file directory.

I have run locally https://httpbin.org/ by using docker Image 
Docker command : docker run -p 80:80 kennethreitz/httpbin
Fire above command in terminal and httpbin docker image will be download in locally and run on 80 port.
URL to check httpbin ==> http://localhost/


Command to RUN :

1) For GET request ====>> 

    httpc get -v http://localhost/get?course=networking&assignment=1

    httpc get -h Content-Type:application/json http://localhost/get?course=networking&assignment=1

    httpc get -v -h Content-Type:application/json http://localhost/get?course=networking&assignment=1

2) For POST request ====>>

    httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post

   httpc post -v -d '{"Assignment":1}' http://localhost/post
	
    httpc post -v -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post

    httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1

    httpc post -v -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1

    httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1 -o result.txt
    
   httpc post -v -f file.json http://localhost/post?course=networking&assignment=1

3) GET request with REDIRECTION CODE (numbers starts with 3xx) ====>>

    httpc get -v http://samples.openweathermap.org/data/2.5/weather?q=London,uk

    httpc get -v http://samples.openweathermap.org/data/2.5/weather?q=London,uk -o result.txt
    
 4) Sample output of above command with Step by step ====>>
    
    
    Please Enter command ==> httpc get -h http://localhost/get?course=networking&assignment=1
=============================================================================>>
{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Http": "//localhost/get?course=networking&assignment=1"
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/get?course=networking&assignment=1"
}
Please Enter command ==> httpc get -v http://localhost/get?course=networking&assignment=1
=============================================================================>>
HTTP/1.1 200 OK
Server: gunicorn/19.9.0
Date: Sat, 05 Oct 2019 03:01:35 GMT
Connection: keep-alive
Content-Type: application/json
Content-Length: 179
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true

{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {}, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/get?course=networking&assignment=1"
}
Please Enter command ==> httpc get -h Content-Type:application/json http://localhost/get?course=networking&assignment=1
=============================================================================>>
{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Content-Type": "application/json"
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/get?course=networking&assignment=1"
}
Please Enter command ==> httpc get -v -h Content-Type:application/json http://localhost/get?course=networking&assignment=1
=============================================================================>>
HTTP/1.1 200 OK
Server: gunicorn/19.9.0
Date: Sat, 05 Oct 2019 03:01:46 GMT
Connection: keep-alive
Content-Type: application/json
Content-Length: 221
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true

{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Content-Type": "application/json"
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/get?course=networking&assignment=1"
}
Please Enter command ==> httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post
=============================================================================>>
{
  "args": {}, 
  "data": "{\"Assignment\":1}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "16", 
    "Content-Type": "application/json"
  }, 
  "json": {
    "Assignment": 1
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/post"
}
Please Enter command ==> httpc post -v -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post
=============================================================================>>
HTTP/1.1 200 OK
Server: gunicorn/19.9.0
Date: Sat, 05 Oct 2019 03:01:56 GMT
Connection: keep-alive
Content-Type: application/json
Content-Length: 268
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true

{
  "args": {}, 
  "data": "{\"Assignment\":1}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "16", 
    "Content-Type": "application/json"
  }, 
  "json": {
    "Assignment": 1
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/post"
}
Please Enter command ==> httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1
=============================================================================>>
{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "data": "{\"Assignment\":1}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "16", 
    "Content-Type": "application/json"
  }, 
  "json": {
    "Assignment": 1
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/post?course=networking&assignment=1"
}
Please Enter command ==> httpc post -v -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1
=============================================================================>>
HTTP/1.1 200 OK
Server: gunicorn/19.9.0
Date: Sat, 05 Oct 2019 03:02:06 GMT
Connection: keep-alive
Content-Type: application/json
Content-Length: 353
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true

{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "data": "{\"Assignment\":1}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "16", 
    "Content-Type": "application/json"
  }, 
  "json": {
    "Assignment": 1
  }, 
  "origin": "172.17.0.1", 
  "url": "http://0.0.0.0/post?course=networking&assignment=1"
}
Please Enter command ==> httpc post -h Content-Type:application/json -d '{"Assignment":1}' http://localhost/post?course=networking&assignment=1 -o result.txt
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
Please Enter command ==> httpc get -v http://samples.openweathermap.org/data/2.5/weather?q=London,uk
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
HTTP/1.1 301 Moved Permanently

<html>
<head><title>301 Moved Permanently</title></head>
<body bgcolor="white">
<center><h1>301 Moved Permanently</h1></center>
<hr><center>openresty/1.9.7.1</center>
</body>
</html>
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
HTTP/1.1 301 Moved Permanently

<html>
<head><title>301 Moved Permanently</title></head>
<body bgcolor="white">
<center><h1>301 Moved Permanently</h1></center>
<hr><center>openresty/1.9.7.1</center>
</body>
</html>
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
HTTP/1.1 301 Moved Permanently

<html>
<head><title>301 Moved Permanently</title></head>
<body bgcolor="white">
<center><h1>301 Moved Permanently</h1></center>
<hr><center>openresty/1.9.7.1</center>
</body>
</html>
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
HTTP/1.1 301 Moved Permanently

<html>
<head><title>301 Moved Permanently</title></head>
<body bgcolor="white">
<center><h1>301 Moved Permanently</h1></center>
<hr><center>openresty/1.9.7.1</center>
</body>
</html>
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
HTTP/1.1 301 Moved Permanently

<html>
<head><title>301 Moved Permanently</title></head>
<body bgcolor="white">
<center><h1>301 Moved Permanently</h1></center>
<hr><center>openresty/1.9.7.1</center>
</body>
</html>
Please Enter command ==> httpc get -v http://samples.openweathermap.org/data/2.5/weather?q=London,uk -o result.txt
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
redirectLocation ==> https://samples.openweathermap.org/data/2.5/weather?q=London,uk
=============================================================================>>
Response has been successfully written in ==> result.txt  File path
Please Enter command ==> 
