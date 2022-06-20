
package com.recipeapp.recipeapp.home;

import com.recipeapp.recipeapp.models.Categories;
import com.recipeapp.recipeapp.models.Meals;
import com.recipeapp.recipeapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    void getMeals() {
        view.showLoading();
        Call<Meals> mealsCall = Utils.getApi().getMeal();
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.setMeal(response.body().getMeals());
                }
                else {
                    view.onErrorLoading(response.message());
                }
            }
            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }


    void getCategories() {
        view.showLoading();
        Call<Categories> categoriesCall = Utils.getApi().getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call,
                                   Response<Categories> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.setCategory(response.body().getCategories());
                }
                else {
                    view.onErrorLoading(response.message());
                }
            }
            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
