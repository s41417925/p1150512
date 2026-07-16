package com.example.p1150512;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.p1150512.entity.Meal;
import com.example.p1150512.service.MealService;

/**
 * @SpringBootTest: 在執行測試方法時，會先啟動一個完整的 Spring 容器，掃描所有需要被託管的物件並
 * 載入到 Spring 容器中託管，所以可以使用 @Autowired 將被託管的物件引入並使用
 * */
@SpringBootTest
public class ServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void testGetAllMeals() {

        List<Meal> mealList = mealService.getAllMeals();

        for (Meal meal : mealList) {
            System.out.println(
                meal.getName() + "，價格：" + meal.getPrice()
            );
        }
    }
}