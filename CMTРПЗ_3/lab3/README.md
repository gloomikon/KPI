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
    <p><b>GET </b><code>http://localhost:8082/users/</code></p>
    <p>Return list of all users</p>
   </li>
   
   <li>
    <p><b>GET </b><code> http://localhost:8082/users/{id}</code></p>
    <p>Return user with following id</p>
  </li>
  
   <li>
    <p><b>POST </b><code> http://localhost:8082/users/</code></p>
    <p>Create user with required params: id, age, name, surname, email, phonenumber</p>
  </li>
  
   <li>
    <p><b>PUT </b><code> http://localhost:8082/users/</code></p>
    <p>Update user with required params: id, age, name, surname, email, phonenumber</p>
  </li>
  
   <li>
    <p><b>DELETE </b><code> http://localhost:8082/users/</code></p>
    <p>Delete user with required param: id</p>
  </li>
</ul>

<h3> Fourth (test configs)</h3>

<p>To update configs make the following when all microservices have started-up:</p>
<ul>
  <li><code>docker container ls</code>//find config-server id</li>
  <li><code>docker exec -it {config-server id} bin/sh</code>//connect to container</li>
  <li><code>cd configdir</code>//here you can update config files</li>
</ul>

<ul>
  <li>
    <p><b>GET </b><code>http://localhost:8888/api-gateway/default</code></p>
    <p>Return api-gateway config</p>
   </li>
   
   <li>
    <p><b>GET </b><code> http://localhost:8081/config</code></p>
    <p>Return first client config</p>
  </li>
  
   <li>
    <p><b>GET </b><code> http://localhost:8082/config/</code></p>
    <p>Return second client config</p>
  </li>

   <li>
    <p><b>GET </b><code> http://localhost:8080/config</code></p>
    <p>Return all configs</p>
  </li>

   <li>
    <p><b>POST </b><code> http://localhost:8081/actuator/refresh/</code></p>
    <p>Refersh config</p>
  </li>
  
   <li>
    <p><b>POST </b><code> http://localhost:8081/actuator/bus-refresh</code></p>
    <p>Refersh bus</p>
  </li>
</ul>
