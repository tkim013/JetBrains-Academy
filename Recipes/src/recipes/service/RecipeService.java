package recipes.service;

import org.springframework.http.ResponseEntity;
import recipes.entity.Recipe;
import recipes.model.IdResponse;
import recipes.model.RecipeResponse;
import recipes.security.service.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    RecipeResponse getRecipe(Long id);

    IdResponse saveRecipe(Recipe recipe, UserDetailsImpl details);

    ResponseEntity<String> updateRecipe(Recipe recipe, Long id, UserDetailsImpl details);

    ResponseEntity<String> deleteRecipe(Long id, UserDetailsImpl details);

    List<RecipeResponse> searchRecipe(Optional<String> name, Optional<String> category);
}
