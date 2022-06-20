package com.recipeapp.recipeapp.models;

public class RecipeModel {
    private String label;
    private String image;
    private String url;
    private String source;
    private String yield;
    private String calories;
    private String totalWeight;


    public RecipeModel() {
       new RecipeModel(label, image, url, source, yield, calories, totalWeight);
    }

    private RecipeModel(String label, String image, String url, String source, String yield, String calories, String totalWeight) {
        this.label = label;
        this.image = image;
        this.url = url;
        this.source = source;
        this.yield = yield;
        this.calories = calories;
        this.totalWeight = totalWeight;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() { return url; }

    public String getSource() {
        return source;
    }

    public String getYield() {
        return yield;
    }

    public String getCalories() {
        return calories;
    }

    public String getTotalWeight() {
        return totalWeight;
    }
}
