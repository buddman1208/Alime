package kr.edcan.alime.utils;

import java.util.Date;
import java.util.List;

import kr.edcan.alime.models.Question;
import kr.edcan.alime.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by JunseokOh on 2016. 8. 16..
 */
public interface NetworkInterface {

    @POST("/auth/login")
    @FormUrlEncoded
    Call<User> userLogin(
            @Field("userid") String userid,
            @Field("password") String password
    );

    @POST("/auth/register")
    @FormUrlEncoded
    Call<User> userRegister(
            @Field("userid") String userid,
            @Field("password") String password,
            @Field("username") String username,
            @Field("isAdmin") boolean isAdmin,
            @Field("attendType") int attendType
    );

    @POST("/question/listArticle")
    Call<List<Question>> listQuestion();

    @POST("/question/newArticle")
    @FormUrlEncoded
    Call<Question> postQuestion(
            @Field("title") String title,
            @Field("date") Date date,
            @Field("content") String content,
            @Field("author") String userid,
            @Field("password") String articlePassword
    );

    @POST("/question/replyArticle")
    @FormUrlEncoded
    Call<String> replyQuestion(
            @Field("userid") String userid,
            @Field("articleid") String articleid,
            @Field("content") String content
    );

    @POST("/question/deleteArticle")
    @FormUrlEncoded
    Call<String> deleteQuestion(
            @Field("userid") String userid,
            @Field("articleid") String articleid
    );
}
