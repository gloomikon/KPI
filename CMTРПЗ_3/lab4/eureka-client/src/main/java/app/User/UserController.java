package app.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RefreshScope
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @Value("${test.fifthprop}")
    private String fifthprope;

    @Value("${test.sixthprop}")
    private String sixprope;

    @GetMapping("/config")
    public @ResponseBody
    HashMap<String, String> getConfig() {
        HashMap<String, String> configmap = new HashMap<String, String>();
        configmap.put("fifth", fifthprope);
        configmap.put("sixth", sixprope);
        return configmap;
    }

    @GetMapping("/")
    public String getInstanceId() {
        return instanceId;
    }

    @GetMapping("/users")
    public String getUserById(@RequestParam(value = "id", required = false) Integer id) {
        if (id == null) {
            Iterable<User> result = userRepository.findAll();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(result);
            return prettyJson;
        }
        else {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = gson.toJson(user);
                return prettyJson;
            }
            return "{}";
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody
                                          @RequestParam(value = "id", required = true) Integer id,
                                          @RequestParam(value = "age", required = true) Integer age,
                                          @RequestParam(value = "name", required = true) String name,
                                          @RequestParam(value = "surname", required = true) String surname,
                                          @RequestParam(value = "email", required = true) String email,
                                          @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        try {
            Optional<User> test = userRepository.findById(id);
            if (test.isPresent()) {
                return ResponseEntity.badRequest()
                        .body("There is a user with given ID already");
            }
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPhonenumber(phonenumber);
            userRepository.save(user);
            return ResponseEntity.ok("OK");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestBody @RequestParam(value = "id", required = true) Integer id,
                                             @RequestParam(value = "age", required = true) Integer age,
                                             @RequestParam(value = "name", required = true) String name,
                                             @RequestParam(value = "surname", required = true) String surname,
                                             @RequestParam(value = "email", required = true) String email,
                                             @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        try {
            Optional<User> test = userRepository.findById(id);
            if (!test.isPresent()) {
                return ResponseEntity.badRequest()
                        .body("There is no user with given ID");
            }
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPhonenumber(phonenumber);
            userRepository.save(user);
            return ResponseEntity.ok("OK");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id) {
        Optional<User> test = userRepository.findById(id);
        if (!test.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("There is no user with given ID");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("OK");
    }
}