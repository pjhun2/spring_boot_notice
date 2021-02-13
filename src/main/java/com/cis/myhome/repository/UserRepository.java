package com.cis.myhome.repository;

import com.cis.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cis.myhome.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


}
