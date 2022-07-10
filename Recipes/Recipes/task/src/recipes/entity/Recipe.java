package recipes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @Size(min = 8)
    private String date;

    @NotBlank
    private String description;

    @NotEmpty
    @Size(min = 1)
    private String[] ingredients;

    @NotEmpty
    @Size(min = 1)
    private String[] directions;

    private String author;
}
