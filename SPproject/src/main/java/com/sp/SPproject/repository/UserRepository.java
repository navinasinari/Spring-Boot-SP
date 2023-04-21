package com.sp.SPproject.repository;
import com.sp.SPproject.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByPassword(String password);
    User findByUsername(String name);
}
