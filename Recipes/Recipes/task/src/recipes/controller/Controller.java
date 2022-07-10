package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.model.IdResponse;
import recipes.model.RecipeResponse;
import recipes.security.service.UserDetailsImpl;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api")
public class Controller {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @PostMapping("/recipe/new")
    IdResponse saveRecipe(@RequestBody @Valid Recipe recipe,
                          @AuthenticationPrincipal UserDetailsImpl details
    ) {

        return this.recipeService.saveRecipe(recipe, details);
    }

    @PostMapping("/register")
    void registerUser(@RequestBody @Valid User user) {

        this.userService.addUser(user);
    }

    @GetMapping("/recipe/{id}")
    RecipeResponse getRecipe(@PathVariable Long id) {

        return this.recipeService.getRecipe(id);
    }

    @GetMapping("/recipe/search")
    List<RecipeResponse> searchRecipe(@RequestParam Optional<String> name,
                              @RequestParam Optional<String> category
    ) {

        return this.recipeService.searchRecipe(name, category);
    }

    @PutMapping("/recipe/{id}")
    ResponseEntity<String> updateRecipe(@Valid @RequestBody Recipe recipe,
                                        @PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl details
    ) {

        return this.recipeService.updateRecipe(recipe, id, details);
    }

    @DeleteMapping("/recipe/{id}")
    ResponseEntity<String> deleteRecipe(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl details
    ) {

        return this.recipeService.deleteRecipe(id, details);
    }
}
