package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.model.Users;

public interface UsersService {
    Users findByUsername (String username);
}
