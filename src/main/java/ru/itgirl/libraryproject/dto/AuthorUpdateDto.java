package ru.itgirl.libraryproject.dto;

import jakarta.validation.constraints.Min;
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
public class AuthorUpdateDto {
    @Min(1)
    private Long id;
    @Size(min = 2, max = 30)
    @NotBlank(message = "Необходимо указать имя автора")
    private String name;
    @Size(min = 2, max = 30)
    @NotBlank(message = "Необходимо указать фамилию автора")
    private String surname;
}
