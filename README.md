## using react and spring to create a web service where users can write and share articles

The application is hosted on azure app service with Tomcat 9.0
#### application link https://sharearticles.azurewebsites.net/

##### Frontend is simple react which is hosted on the same server inside static files
##### Backend is spring web with maven
Article creation and view is done through rest APIs using spring.

Each article gets stored as a article object on the server which is used to view them and for avoiding multiple articles with the same name/url a serializable red black tree is used (i could have used an array, but tree will provide better complexities) which stores the article object name/url.

After uploading an article the server responds with the link, with which it can be accessed later.

##### Views count on each article depends on following
##### 1. Cookies
##### 2. IP and location <i>(Only when cookies are deleted)</i>
##### 3. Browser, OS, Device name <i>(Only when cookies are deleted)</i>

