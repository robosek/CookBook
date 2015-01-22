package com.example.komputer_robo.cookbook.ShowRecipes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.komputer_robo.cookbook.RecipeView;
import com.example.komputer_robo.cookbook.RecipeView_;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collections;
import java.util.List;


@EBean
public class RecipeFavouriteListAdapter extends BaseAdapter {

    private List<Recipe> lista;

    public void setList(List<Recipe> lista) {

        this.lista = lista;
    }

    @RootContext
    Context context;


    public RecipeFavouriteListAdapter() {
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Recipe getItem(int i) {
        return lista.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        RecipeView recipeView;

        if (view == null) {
            recipeView = RecipeView_.build(context);
        } else {
            recipeView = (RecipeView) view;
        }

        recipeView.recipeBind(getItem(i));

        return recipeView;

    }


    public void updateCookbook(Recipe recipe) {

        lista.add(recipe);

        Collections.sort(lista);

        notifyDataSetChanged();

    }

}








