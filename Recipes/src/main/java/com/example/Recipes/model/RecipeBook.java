package com.example.Recipes.model;

import com.example.Recipes.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class RecipeBook {

    private List<Recipe> recipeList;
    private Map<Long, Integer> idMap;
    private long idCounter;

    public RecipeBook() {
        this.recipeList = new ArrayList<>();
        this.idMap = new HashMap<>();
        this.idCounter = 1;
    }

    public long generateId(int index) {
        this.idMap.put(idCounter, index);
        return this.idCounter++;
    }
}
