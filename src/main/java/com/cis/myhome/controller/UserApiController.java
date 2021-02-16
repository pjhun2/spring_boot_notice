package com.cis.myhome.controller;

import com.cis.myhome.model.Board;
import com.cis.myhome.model.User;
import com.cis.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {


        @Autowired
        private UserRepository repository;

        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/users")
        List<User> all() {
            return repository.findAll();
        }
        // end::get-aggregate-root[]

        @PostMapping("/users")
        User newuser(@RequestBody User newuser) {
            return repository.save(newuser);
        }

        // Single item

        @GetMapping("/users/{id}")
        User one(@PathVariable Long id) {

            return repository.findById(id).orElse(null);

        }

        @PutMapping("/users/{id}")
        User replaceuser(@RequestBody User newuser, @PathVariable Long id) {

            return repository.findById(id)
                    .map(user -> {
//                        user.setTitle(newuser.getTitle());
//                        user.setContent(newuser.getContent());
                       // user.setBoards(newuser.getBoards());
                        user.getBoards().clear();
                        user.getBoards().addAll(newuser.getBoards());
                        for(Board board : user.getBoards()) {
                            board.setUser(user);
                        }
                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        newuser.setId(id);
                        return repository.save(newuser);
                    });
        }

        @DeleteMapping("/users/{id}")
        void deleteuser(@PathVariable Long id) {
            repository.deleteById(id);
        }
    }

