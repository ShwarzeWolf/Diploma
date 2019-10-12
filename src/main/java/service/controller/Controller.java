package service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private String name = "";
    private String password = "";

    @RequestMapping(path="/join", method = RequestMethod.GET)
    public @ResponseBody String addNewUser (@RequestParam String name,
                                            @RequestParam String password) {
        this.name = name;
        this.password = password;

        return "User with name " + name + " with password " + password + " was successfully saved" ;
    }

    @RequestMapping(path="/login", method = RequestMethod.GET)
    public @ResponseBody String loginIntoSystem(@RequestParam String name,
                                                @RequestParam String password) {
        if (this.name.equals(name) && this.password.equals(password))
            return "authorisation was successful";
        else
            return "authorisation failed";
    }
}
