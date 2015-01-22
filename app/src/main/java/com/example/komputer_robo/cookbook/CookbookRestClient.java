package com.example.komputer_robo.cookbook;

import com.example.komputer_robo.cookbook.Comments.Comment;
import com.example.komputer_robo.cookbook.Comments.CommentsList;
import com.example.komputer_robo.cookbook.Pictures.Picture;
import com.example.komputer_robo.cookbook.Pictures.PictureList;
import com.example.komputer_robo.cookbook.Likes.Like;
import com.example.komputer_robo.cookbook.Likes.LikesList;
import com.example.komputer_robo.cookbook.Login.EmailAndPassword;
import com.example.komputer_robo.cookbook.Login.User;
import com.example.komputer_robo.cookbook.Recipe.Recipe;
import com.example.komputer_robo.cookbook.SignUp.AccountUserData;
import com.example.komputer_robo.cookbook.UserInfo.UserInfo;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;



@Rest(rootUrl = "http://pegaz.wzr.ug.edu.pl:8080/rest", converters = {MappingJackson2HttpMessageConverter.class })
@RequiresHeader({"X-Dreamfactory-Application-Name"})

public interface CookbookRestClient extends RestClientHeaders {


    //For Recipes
    @Get("/db/recipes")
    public Cookbook getCookbook();

    @Get("/db/recipes/{id}")
    Recipe getCookbookById(int id);

    @Post("/db/recipes")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    Recipe sendRecipe(Recipe recipe);

    @Delete("/db/recipes/{id}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deleteRecipe(Integer id);

    @Put("/db/recipes")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void editRecipe(Recipe recipe);



    //For authorization
    @Post("/user/session")
    User login(EmailAndPassword emailAndPassword);

    @Post("/user/register/?login=true")
    User signUp(AccountUserData userData);




    //For Pictures
    @Get("/db/pictures?filter={path}")
    PictureList getPictureListById(String path);

    @Post("/db/pictures")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void sendPicture(Picture picture);

    @Delete("/db/pictures?filter={path}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deletePicture(String path);


    //For Comments
    @Get("/db/comments?filter={path}")
    public CommentsList getCommentsById(String path);

    @Post("/db/comments")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void sendComment(Comment comment);

    @Delete("/db/comments?filter={path]")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deleteComments(String path);


    //For Likes
    @Get("/db/likes?filter={path}")
    LikesList getLikes(String path);

    @Delete("/db/likes/{id}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deleteLike(int id);

    @Post("/db/likes")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void sendLike(Like like);

    @Delete("/db/likes?filter={path}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deleteLikes(String path);



    //For info about user
    @Get("/system/user/{id}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    UserInfo getUserInfo(int id);
























}
