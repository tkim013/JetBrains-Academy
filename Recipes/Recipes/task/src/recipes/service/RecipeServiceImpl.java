package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.exception.AuthorizationException;
import recipes.exception.IdNotFoundException;
import recipes.exception.QueryParameterException;
import recipes.model.IdResponse;
import recipes.model.RecipeResponse;
import recipes.repository.RecipeRepository;
import recipes.security.service.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public RecipeResponse getRecipe(Long id) {

        Optional<Recipe> o = this.recipeRepository.findById(id);

        if(o.isEmpty()) {
            throw new IdNotFoundException("(Not found)");
        }

        return new RecipeResponse(
                o.get().getName(),
                o.get().getCategory(),
                o.get().getDate(),
                o.get().getDescription(),
                o.get().getIngredients(),
                o.get().getDirections());
    }

    @Override
    public IdResponse saveRecipe(Recipe recipe, UserDetailsImpl details) {

        recipe.setAuthor(details.getEmail());
        recipe.setDate(LocalDateTime.now().toString());

        this.recipeRepository.save(recipe);

        return new IdResponse(recipe.getId());
    }

    @Override
    public ResponseEntity<String> updateRecipe(Recipe recipe, Long id, UserDetailsImpl details) {

        Optional<Recipe> o = this.recipeRepository.findById(id);

        if(o.isEmpty()) {
            throw new IdNotFoundException("(Not found)");
        }

        if(!details.getEmail().equals(o.get().getAuthor())) {
            throw new AuthorizationException("(Forbidden)");
        }

        Recipe update = o.get();
        update.setName(recipe.getName());
        update.setCategory(recipe.getCategory());
        update.setDescription(recipe.getDescription());
        update.setIngredients(recipe.getIngredients());
        update.setDirections(recipe.getDirections());
        update.setDate(LocalDateTime.now().toString());

        recipeRepository.save(update);

        return new ResponseEntity<>("(No Content)", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<String> deleteRecipe(Long id, UserDetailsImpl details) {

        Optional<Recipe> o = this.recipeRepository.findById(id);

        if(o.isEmpty()) {
            throw new IdNotFoundException("(Not found)");
        }

        if(!details.getEmail().equals(o.get().getAuthor())) {
            throw new AuthorizationException("(Forbidden)");
        }

        this.recipeRepository.deleteById(id);

        return new ResponseEntity<>("(No Content)", HttpStatus.NO_CONTENT);
    }

    @Override
    public List<RecipeResponse> searchRecipe(Optional<String> name, Optional<String> category) {

        if (name.isEmpty() && category.isEmpty() || name.isPresent() && category.isPresent()) {

            throw new QueryParameterException("(Bad Request)");
        }

        List<Recipe> list;
        List<RecipeResponse> rList;

        list = name.isPresent() ? this.recipeRepository.searchByName(name.get().toLowerCase()) :
                this.recipeRepository.searchByCategory(category.get().toLowerCase());

        rList = new ArrayList<>();

        for (Recipe r : list) {

            RecipeResponse recipeResponse = new RecipeResponse();
            recipeResponse.setName(r.getName());
            recipeResponse.setCategory(r.getCategory());
            recipeResponse.setDate(r.getDate());
            recipeResponse.setDescription(r.getDescription());
            recipeResponse.setIngredients(r.getIngredients());
            recipeResponse.setDirections(r.getDirections());

            rList.add(recipeResponse);
        }

        return rList;
    }
}
