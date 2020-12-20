package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "spring!!");
        return "hello"; //여기서 hello 는 hello.html
    }

    @GetMapping("hello-mvc")
    public String helloNvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name); //파라미터로 넘어온 name을 넘겨줌.
        return "hello-template";
    }

}
