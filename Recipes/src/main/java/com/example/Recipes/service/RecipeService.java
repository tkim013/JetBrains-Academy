package com.example.Recipes.service;

import com.example.Recipes.entity.Recipe;
import com.example.Recipes.model.IdResponse;
import com.example.Recipes.model.RecipeResponse;
import com.example.Recipes.security.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    RecipeResponse getRecipe(Long id);

    IdResponse saveRecipe(Recipe recipe, UserDetailsImpl details);

    ResponseEntity<String> updateRecipe(Recipe recipe, Long id, UserDetailsImpl details);

    ResponseEntity<String> deleteRecipe(Long id, UserDetailsImpl details);

    List<RecipeResponse> searchRecipe(Optional<String> name, Optional<String> category);
}
