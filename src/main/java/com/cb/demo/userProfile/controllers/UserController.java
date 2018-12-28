package com.cb.demo.userProfile.controllers;

import com.cb.demo.userProfile.model.UserEntity;
import com.cb.demo.userProfile.service.UserService;
import com.cb.demo.userProfile.service.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String findByUsername() {
        return "test";
    }

    @GetMapping("/{id}")
    public UserVO findByUsername(@PathVariable("id") String id) {
        return userService.getUser(id);
    }
//
    @GetMapping("/save")
    public UserEntity save( @Valid @RequestBody UserEntity user) {
        return userService.save(user);
    }
//
//    @GetMapping(value="/list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<UserEntity> save( @RequestParam("tenantId") Integer tenantId,
//                                  @RequestParam("countryCode") String countryCode,
//                                  @RequestParam("limit") int limit,
//                                  @RequestParam("offset") int offset) {
//        return reactiveUserRepository.findByCountryAndTenantId(tenantId, countryCode, limit, offset);
//    }
}
