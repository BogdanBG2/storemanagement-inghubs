package com.bogdan.storemanagement_inghubs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/unauthenticated")
  public String test() {
    return "test";
  }

  @GetMapping("/authenticated")
  public String test2() {
    return "test2";
  }
}
