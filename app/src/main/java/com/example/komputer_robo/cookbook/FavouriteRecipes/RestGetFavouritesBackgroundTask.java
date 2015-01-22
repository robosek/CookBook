package com.example.komputer_robo.cookbook.FavouriteRecipes;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Likes.Like;
import com.example.komputer_robo.cookbook.Likes.LikesList;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.ShowFavouritesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestGetFavouritesBackgroundTask {

    @RootContext
    ShowFavouritesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getFavourites(User user) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            LikesList likesList = restClient.getLikes("ownerId=" + user.id);

            if (likesList.likes.size() > 0) {

                for (Like like : likesList.likes) {

                    Recipe recipe = restClient.getCookbookById(like.recipeId);

                    publishResult(recipe);

                }

            }
            else{

                throw new Exception("Brak ulubionych przepisów!");

            }

        } catch (Exception ex) {

            publishError(ex);
        }

    }

    @UiThread
    void publishResult(Recipe recipe) {

        activity.updateCookbook(recipe);


    }

    @UiThread
    void publishError(Exception ex) {

        activity.showError(ex);
    }


}















