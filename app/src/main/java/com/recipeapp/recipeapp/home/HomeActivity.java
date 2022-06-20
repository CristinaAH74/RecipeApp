package com.recipeapp.recipeapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.recipeapp.recipeapp.apis.APIClient;
import com.recipeapp.recipeapp.apis.APICredentials;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.details.DetailActivity;
import com.recipeapp.recipeapp.adapter.SearchRecipeAdapter;
import com.recipeapp.recipeapp.adapter.RecyclerViewHomeAdapter;
import com.recipeapp.recipeapp.adapter.ViewPagerHeaderAdapter;
import com.recipeapp.recipeapp.category.CategoryActivity;
import com.recipeapp.recipeapp.models.Categories;
import com.recipeapp.recipeapp.models.Meals;
import com.recipeapp.recipeapp.models.RootObjectModel;
import com.recipeapp.recipeapp.profile.UserProfileActivity;
import com.recipeapp.recipeapp.response.SearchRecipes;
import com.recipeapp.recipeapp.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements HomeView {
    FirebaseAuth firebaseAuth;
    private static final String TAG ="\t" + HomeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private SearchRecipeAdapter.RecyclerViewClickListener listener;
    private ArrayList<RootObjectModel> recipes;
    private SearchRecipeAdapter adapter;
    boolean pressAgaintoExit = false;

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    @BindView(R.id.viewPagerHeader)
    ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory)
    RecyclerView recyclerViewCategory;

    HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        search();
    }

    //initialize data from theMealDB
    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();
    }

    //search bar
    private void search(){
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Type here to search");
        searchView.onActionViewCollapsed();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        setOnClickListener(); // First API, go to DetailActivity
    }

    //search recipe through EDAMAM
    private void searchRecipes(String query){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APICredentials.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIClient apiClient = retrofit.create(APIClient.class);
        Call<SearchRecipes> searchRecipesCall = apiClient.searchRecipes(APICredentials.TYPE, query, APICredentials.APP_ID, APICredentials.API_KEY);

        searchRecipesCall.enqueue(new Callback<SearchRecipes>() {
            @Override
            public void onResponse(Call<SearchRecipes> call, Response<SearchRecipes> response) {
                if(response.isSuccessful() && response.body() != null){
                    recipes=new ArrayList<>(Arrays.asList(response.body().getFoodRecipes()));
                    for(int i=0; i<recipes.size(); i++) {
                        RootObjectModel rootObjectModel = recipes.get(i);
                        Log.v("Tag" + TAG, "label\t" + rootObjectModel.getRecipeModel().getLabel());
                    }
                }
                setContentView(R.layout.items_recycler_view);
                recyclerView = findViewById(R.id.recycler_view);
                adapter = new SearchRecipeAdapter(HomeActivity.this, recipes, listener);
                recyclerView.setAdapter(adapter);
                search();
            }
            @Override
            public void onFailure(Call<SearchRecipes> call, Throwable t) {
                Log.v("Tag"+TAG, "onFailure()"+t.getMessage());
            }
        });
    }

//Second API, show meals
    @Override
        public void showLoading() {
            findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
            findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
        }

        @Override
        public void hideLoading() {
            findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
            findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
        }

        @Override
        public void setMeal(List<Meals.Meal> meal) {
            ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
            viewPagerMeal.setAdapter(headerAdapter);
            viewPagerMeal.setPadding(20, 0, 150, 0);
            headerAdapter.notifyDataSetChanged();

            headerAdapter.setOnItemClickListener((view, position) -> {
                TextView mealName = view.findViewById(R.id.mealName);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
                startActivity(intent);
            });
        }

        @Override
        public void setCategory(List<Categories.Category> category) {
            RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
            recyclerViewCategory.setAdapter(homeAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                    GridLayoutManager.VERTICAL, false);
            recyclerViewCategory.setLayoutManager(layoutManager);
            recyclerViewCategory.setNestedScrollingEnabled(true);
            homeAdapter.notifyDataSetChanged();

            homeAdapter.setOnItemClickListener((view, position) -> {
                Intent intent = new Intent(this, CategoryActivity.class);
                intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
                intent.putExtra(EXTRA_POSITION, position);
                startActivity(intent);
            });
        }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }


    //move to DetailActivity
    private void setOnClickListener() {
        listener = new SearchRecipeAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("label",recipes.get(position).getRecipeModel().getLabel());
                intent.putExtra("image",recipes.get(position).getRecipeModel().getImage());
                intent.putExtra("url", recipes.get(position).getRecipeModel().getUrl());
                intent.putExtra("source",recipes.get(position).getRecipeModel().getSource());
                intent.putExtra("yield", recipes.get(position).getRecipeModel().getYield());
                intent.putExtra("calories", recipes.get(position).getRecipeModel().getCalories());
                intent.putExtra("totalWeight", recipes.get(position).getRecipeModel().getTotalWeight());
                startActivity(intent);
            }
        };
    }

    //On Back Pressed
    @Override
    public void onBackPressed(){
        recyclerView.setVisibility(View.GONE);
        setContentView(R.layout.activity_home);
        init();
        search();
        if(pressAgaintoExit){
            super.onBackPressed();
            //finish
        }
        else{
            pressAgaintoExit = true;
            Toast.makeText(this,"Press Back again to Exit", Toast.LENGTH_SHORT).show();

            int intervalTime = 1000;
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    pressAgaintoExit = false;
                }
            }, intervalTime);
        }

    }

    //Go to Profile ativity
    public void profile(View view){
        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        finish();
    }

}