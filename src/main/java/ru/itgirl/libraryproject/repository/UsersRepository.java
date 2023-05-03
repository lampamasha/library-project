package ru.itgirl.libraryproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.model.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
