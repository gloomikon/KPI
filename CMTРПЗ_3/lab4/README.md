<h2> Requirements </h2>
<ul>
  <li> doocker </li>
  <li> docker compose </li>
</ul>

<h2> Usage </h2>
<h3> First </h3>
<ul>
  <li> <code> cd lab3/config-server/configdir </code> </li>
  <li> <code> git init </code> </li>
  <li> <code> git add . </code> </li>
  <li> <code> git commit -m "Commit message </code> </li>
  <li> <code> cd ... </code> // you have to be in root directory  </li>
</ul>

<h3> Second </h3>
<ul>
  <li> <code> docker-compose build </code> </li>
  <li> <code> docker-compose up --scale eureka-client=2 </code> </li>
</ul>

<h3> Third (test client)</h3>

<ul>
  <li>
    <p><b>GET </b><code>http://localhost:8080/users/</code></p>
    <p>Return list of all users</p>
   </li>
   
   <li>
    <p><b>GET </b><code> http://localhost:8080/users?id=1/</code></p>
    <p>Return user with following id</p>
  </li>
  
   <li>
    <p><b>POST </b><code> http://localhost:8080/users/</code></p>
    <p>Create user with required params: id, age, name, surname, email, phonenumber</p>
  </li>
  
   <li>
    <p><b>PUT </b><code> http://localhost:8080/users/</code></p>
    <p>Update user with required params: id, age, name, surname, email, phonenumber</p>
  </li>
  
   <li>
    <p><b>DELETE </b><code> http://localhost:8080/users/</code></p>
    <p>Delete user with required param: id</p>
  </li>
</ul>

<h3> Fourth</h3>
To check retry, we can stop DB, make a request and then start DB again. 
<ul>
  <li><code>docker container ls</code></li>
  <li><code>docker stop <container id></code></li>
  <li><code>docker start <container id></code></li>
</ul>
In Grafana we can see analytics graph
<img src="https://github.com/gloomikon/java/blob/master/.media/Screenshot%202020-05-11%20at%2015.04.01.png" height="300px">
