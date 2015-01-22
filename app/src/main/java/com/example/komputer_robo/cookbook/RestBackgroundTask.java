package com.example.komputer_robo.cookbook;

import com.example.komputer_robo.cookbook.Pictures.Picture;
import com.example.komputer_robo.cookbook.Pictures.PictureList;
import com.example.komputer_robo.cookbook.ShowRecipes.ShowRecipesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;



@EBean
public class RestBackgroundTask {

    @RootContext
    ShowRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getCookbook() {

        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            Cookbook cookbook = restClient.getCookbook();
            //Pobiera tylko te zdjęcia, które mają przypisany przepis
            PictureList pictureList = restClient.getPictureListById("recipeId!='null'");

            for (Recipe recipe : cookbook.records) {

                if (pictureList.pictureList.size() > 0) {


                    for (Picture picture : pictureList.pictureList) {
                        //Szuka zdjęcia dedykowanego do przepisu
                        if (picture.recipeId == recipe.id) {

                            recipe.pictureBytes = picture.base64bytes;
                            pictureList.pictureList.remove(picture);
                            break;

                        }

                    }


                }


                publishResult(recipe);


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





















