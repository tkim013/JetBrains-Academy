package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import recipes.entity.Recipe;

import java.util.*;

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
