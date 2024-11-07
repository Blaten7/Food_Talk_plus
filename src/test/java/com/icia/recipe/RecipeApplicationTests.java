package com.icia.recipe;

import com.icia.recipe.repository.FoodItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SpringBootTest
class RecipeApplicationTests {

    FoodItemRepository fr;

}
