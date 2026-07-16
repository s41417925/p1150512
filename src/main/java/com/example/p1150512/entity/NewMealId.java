package com.example.p1150512.entity;

import java.io.Serializable;
import java.util.Objects;

public class NewMealId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String cookingStyle;

    // JPA 必須要有無參數建構子
    public NewMealId() {
    }

    public NewMealId(String name, String cookingStyle) {
        this.name = name;
        this.cookingStyle = cookingStyle;
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

    /*
     * 用來判斷兩組主鍵是否相同。
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        NewMealId other = (NewMealId) obj;

        return Objects.equals(name, other.name)
                && Objects.equals(cookingStyle, other.cookingStyle);
    }

    /*
     * 產生主鍵的雜湊值。
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, cookingStyle);
    }
}