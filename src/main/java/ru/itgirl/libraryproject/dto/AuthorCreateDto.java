package ru.itgirl.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    @Size(min = 2, max = 10)
    @NotBlank(message = "Необходимо указать имя автора")
    private String name;
    @Size(min = 2, max = 30)
    @NotBlank(message = "Необходимо указать фамилию автора")
    private String surname;
}