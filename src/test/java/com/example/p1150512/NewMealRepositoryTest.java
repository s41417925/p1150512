package com.example.p1150512;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.p1150512.dao.NewMealRepository;
import com.example.p1150512.entity.NewMeal;

@SpringBootTest
public class NewMealRepositoryTest {

    @Autowired
    private NewMealRepository newMealRepository;

    @Test
    public void testSave() {

        NewMeal newMeal = new NewMeal(
                "雞肉",
                "炸",
                80
        );

        newMealRepository.save(newMeal);

        System.out.println("新增完成");
    }

    @Test
    public void testFindAll() {

        List<NewMeal> mealList =
                newMealRepository.findAll();

        for (NewMeal meal : mealList) {
            System.out.println(
                    "名稱：" + meal.getName()
                    + "，料理方式：" + meal.getCookingStyle()
                    + "，價格：" + meal.getPrice()
            );
        }
    }
}