package com.cis.myhome.controller;

import java.util.List;

import com.cis.myhome.model.Board;
import com.cis.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;

@RestController
@RequestMapping("/api")
public class BoardApiController {


        @Autowired
        private BoardRepository repository;

        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/boards")
        List<Board> all(@RequestParam(required = false) String title,
        @RequestParam(required = false, defaultValue = "") String content) {
            if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
                return repository.findAll();
            } else {
                return repository.findByTitleOrContent(title, content);
            }
        }
        // end::get-aggregate-root[]

        @PostMapping("/boards")
        Board newboard(@RequestBody Board newboard) {
            return repository.save(newboard);
        }

        // Single item

        @GetMapping("/boards/{id}")
        Board one(@PathVariable Long id) {

            return repository.findById(id).orElse(null);

        }

        @PutMapping("/boards/{id}")
        Board replaceboard(@RequestBody Board newboard, @PathVariable Long id) {

            return repository.findById(id)
                    .map(board -> {
                        board.setTitle(newboard.getTitle());
                        board.setContent(newboard.getContent());
                        return repository.save(board);
                    })
                    .orElseGet(() -> {
                        newboard.setId(id);
                        return repository.save(newboard);
                    });
        }

        @DeleteMapping("/boards/{id}")
        void deleteboard(@PathVariable Long id) {
            repository.deleteById(id);
        }
    }

