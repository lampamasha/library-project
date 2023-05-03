package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
