package com.example.p1150512.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "new_meal")
@IdClass(NewMealId.class)
public class NewMeal {

    /**
     * 第一個主鍵：餐點名稱
     */
    @Id
    @Column(name = "name")
    private String name;

    /**
     * 第二個主鍵：料理方式
     *
     * Java 使用 cookingStyle，
     * 資料庫欄位名稱是 cooking_style。
     */
    @Id
    @Column(name = "cooking_style")
    private String cookingStyle;

    /**
     * 餐點價格
     */
    @Column(name = "price")
    private int price;

    // JPA 必須要有無參數建構子
    public NewMeal() {
    }

    public NewMeal(String name, String cookingStyle, int price) {
        this.name = name;
        this.cookingStyle = cookingStyle;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingStyle() {
        return cookingStyle;
    }

    public void setCookingStyle(String cookingStyle) {
        this.cookingStyle = cookingStyle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}