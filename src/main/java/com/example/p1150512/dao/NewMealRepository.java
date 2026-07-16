package com.example.p1150512.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.p1150512.entity.NewMeal;
import com.example.p1150512.entity.NewMealId;

@Repository
public interface NewMealRepository
        extends JpaRepository<NewMeal, NewMealId> {

    // 題目說不須定義任何方法，因此這裡保持空白。
}