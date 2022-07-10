package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {
    private String name;
    private String category;
    private String date;
    private String description;
    private String[] ingredients;
    private String[] directions;
}
