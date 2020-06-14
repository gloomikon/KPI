package app.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "eureka-client")
public interface UserClient {
    @GetMapping("/config")
    @ResponseBody
    Map<String, String> getConfig();

    @GetMapping("/")
    String getInstanceId();

    @GetMapping("/users")
    public String getAll();

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable int id);

    @PostMapping("/users")
    public Boolean addUser(@RequestBody
                           @RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "age", required = true) Integer age,
                           @RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "surname", required = true) String surname,
                           @RequestParam(value = "email", required = true) String email,
                           @RequestParam(value = "phonenumber", required = true) String phonenumber);

    @PutMapping("/users")
    public Boolean updateUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id,
                              @RequestParam(value = "age", required = true) Integer age,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "surname", required = true) String surname,
                              @RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "phonenumber", required = true) String phonenumber);

    @DeleteMapping("/users")
    public Boolean deleteUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id);
}
