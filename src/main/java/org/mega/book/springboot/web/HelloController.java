package org.mega.book.springboot.web;

import org.mega.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        System.out.print(name);
        System.out.println(amount + "----------------------------------");
        return new HelloResponseDto(name, amount);
        //http://localhost:8080/hello/dto?name=한국&amount=10
        //url 없어도 데이터가 맞으면 가져올 수 있다 (post 아니고 get!)
    }
}
