package app.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
    public String getAll() {
        Iterable<User> result = userRepository.findAll();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(result);
        return prettyJson;
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(user);
            return prettyJson;
        }
        return "{}";
    }

    @PostMapping("/users")
    public Boolean addUser(@RequestBody
                               @RequestParam(value = "id", required = true) Integer id,
                               @RequestParam(value = "age", required = true) Integer age,
                               @RequestParam(value = "name", required = true) String name,
                               @RequestParam(value = "surname", required = true) String surname,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        Optional<User> test = userRepository.findById(id);
        if (test.isPresent()) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        userRepository.save(user);

        return true;
    }

    @PutMapping("/users")
    public Boolean updateUser(@RequestBody
                                  @RequestParam(value = "id", required = true) Integer id,
                              @RequestParam(value = "age", required = true) Integer age,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "surname", required = true) String surname,
                              @RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "phonenumber", required = true) String phonenumber) {
        Optional<User> test = userRepository.findById(id);
        if (!test.isPresent()) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        userRepository.save(user);

        return true;
    }

    @DeleteMapping("/users")
    public Boolean deleteUser(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id) {
        Optional<User> test = userRepository.findById(id);
        if (!test.isPresent()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}