package com.example.komputer_robo.cookbook.DeleteRecipe;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;



@EBean
public class RestDeleteBackgroundTask
{

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void deletedRecipe(User user,Recipe recipe){

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name","cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token",user.sessionId);
            restClient.deletePicture("recipeId="+recipe.id);


            //Jest problem przy usuwaniu komentarzy, lub lajków dodanych przez innych użytkowników. Nie można ich usunąć, przez co nie można równiez usunąć przpeisu (klucz obcy w tabeli w przepis)
            //restClient.deleteComments("recipeId="+recipe.id
            //restClient.deleteLikes("recipeId="+recipe.id);


            restClient.deleteRecipe(recipe.id);
            publishResult();



        }
        catch(Exception ex){

            publishError(ex);
        }

    }

    @UiThread
    void publishResult(){

        activity.deleteSuccess();



    }

    @UiThread
    void publishError(Exception ex){

            activity.publishError(ex);
    }


}
