package com.example.komputer_robo.cookbook.Comments;

import com.example.komputer_robo.cookbook.CookbookRestClient;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.ShowRecipes.FullRecipesActivity;
import com.example.komputer_robo.cookbook.Recipe.Recipe;
import com.example.komputer_robo.cookbook.UserInfo.UserInfo;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;


@EBean
public class RestGetCommentsBackgroundTask {

    @RootContext
    FullRecipesActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getComments(User user, Recipe recipe) {

        try {


            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            CommentsList comments = restClient.getCommentsById("recipeId=" + recipe.id);

            for (Comment comment : comments.commentList) {

                UserInfo userInfo = restClient.getUserInfo(comment.ownerId);

                if (userInfo.firstName != null)
                    comment.text = userInfo.firstName + ": " + comment.text;
                else
                    comment.text = userInfo.email + ": " + comment.text;


            }


            publishResult(comments);


        } catch (Exception ex) {

            publishError(ex);
        }

    }

    @UiThread
    void publishResult(CommentsList commentsList) {

        activity.commentsDownloadedSuccess(commentsList);


    }

    @UiThread
    void publishError(Exception ex) {

        activity.publishError(ex);
    }


}
