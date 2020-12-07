package io.github.macmuzyka.todoapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Raweshau
 * on 07.12.2020
 */
@RestController
class InfoController {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${my.prop}")
    private String myProp;

    @GetMapping("/info/url")
    String url(){
        return url;
    }
    @GetMapping("/info/prop")
    String myProp() {
        return myProp;
    }
}
