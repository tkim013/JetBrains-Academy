package com.example.Recipes.repository;

import com.example.Recipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE %?1% ORDER BY r.date DESC")
    List<Recipe> searchByName(String name);

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.category) = ?1 ORDER BY r.date DESC")
    List<Recipe> searchByCategory(String category);
}
