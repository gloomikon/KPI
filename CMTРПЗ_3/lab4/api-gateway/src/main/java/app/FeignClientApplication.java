package app;

import app.User.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
@RestController
@EnableAutoConfiguration
@RefreshScope
@EnableRetry
@EnableCircuitBreaker
public class FeignClientApplication {
    @Autowired
    ProxyService userClient;
//    UserClient userClient;

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }

    @Autowired
    ConfigClientAppConfiguration configClientAppConfiguration;

    @RequestMapping(path = "/config", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, String> getConfig() {
        HashMap<String, String> configmap = new HashMap<String, String>();
        configmap.put("firstprop", configClientAppConfiguration.getFirstprop());
        configmap.put("secondprop", configClientAppConfiguration.getSecondprop());
        configmap.put("thirdprop", configClientAppConfiguration.getThirdprop());
        configmap.put("fourhprop", configClientAppConfiguration.getFourthprop());
        configmap.putAll(userClient.getConfig());
        return configmap;
    }

    @GetMapping("/")
    public String getInstanceId() {
        return userClient.getInstanceId();
    }

    @GetMapping("/users")
    public String getUserById(@RequestParam(value = "id", required = false) Integer id) {
        return userClient.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody
                                          @RequestParam(value = "id", required = true) Integer id,
                                          @RequestParam(value = "age", required = true) Integer age,
                                          @RequestParam(value = "name", required = true) String name,
                                          @RequestParam(value = "surname", required = true) String surname,
                                          @RequestParam(value = "email", required = true) String email,
                                          @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        return userClient.addUser(id, age, name, surname, email, phonenumber);
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestBody
                                             @RequestParam(value = "id", required = true) Integer id,
                                             @RequestParam(value = "age", required = true) Integer age,
                                             @RequestParam(value = "name", required = true) String name,
                                             @RequestParam(value = "surname", required = true) String surname,
                                             @RequestParam(value = "email", required = true) String email,
                                             @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        return userClient.updateUser(id, age, name, surname, email, phonenumber);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id) {
        return userClient.deleteUser(id);
    }

}
