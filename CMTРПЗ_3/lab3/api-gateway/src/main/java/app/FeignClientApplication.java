package app;

import app.User.UserClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
@RestController
@RefreshScope
public class FeignClientApplication {
    @Autowired
    UserClient userClient;

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
    public String getAll() {
        return userClient.getAll();
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable int id) {
        return userClient.getUserById(id);
    }

    @PostMapping("/users")
    public Boolean addUser(@RequestBody
                           @RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "age", required = true) Integer age,
                           @RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "surname", required = true) String surname,
                           @RequestParam(value = "email", required = true) String email,
                           @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        return userClient.addUser(id, age, name, surname, email, phonenumber);
    }

    @PutMapping("/users")
    public Boolean updateUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id,
                              @RequestParam(value = "age", required = true) Integer age,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "surname", required = true) String surname,
                              @RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        return userClient.updateUser(id, age, name, surname, email, phonenumber);
    }

    @DeleteMapping("/users")
    public Boolean deleteUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id) {
        return userClient.deleteUser(id);
    }
}
