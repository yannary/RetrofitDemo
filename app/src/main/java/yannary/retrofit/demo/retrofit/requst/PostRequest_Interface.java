package yannary.retrofit.demo.retrofit.requst;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import yannary.retrofit.demo.retrofit.data.Translation1;

/**
 * Created by zhangyanyan on 2018/2/27.
 */

/**
 * 采用@Post表示Post方法进行请求（传入部分url地址）
 * 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
 * 需要配合@Field 向服务器提交需要的字段
 */
public interface PostRequest_Interface {
    /**
     * 一个参数
     *
     * @param targetSentence
     * @return
     */
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation1> getCall(@Field("i") String targetSentence);

    /**
     * 多个参数
     *
     * @param map
     * @return
     */
    @POST("xg_shdz.jsp")
    @FormUrlEncoded
    Call<ResponseBody> getAddressBody(@FieldMap Map<String, Object> map);

    /**
     * 单个文件上传
     *
     * @param description
     * @param file
     * @return
     */
    @Multipart
    @POST("upload.jsp")
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 多文件上传 方法一
     * 通过 MultipartBody和@body作为参数来实现多文件上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("upload.jsp")
    Call<ResponseBody> uploadFileWithRequestBody(@Body MultipartBody multipartBody);

    /**
     * 多文件上传 方法二
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     *
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("upload.jsp")
    Call<ResponseBody> uploadFilesWithParts(@Part("description") RequestBody description,@Part() List<MultipartBody.Part> parts);


    /*******************************************   黑乎乎的分界线  *****************************************************/
    /**
     * Map的key作为表单的键
     * <p>
     * 演示 @FieldMap
     * Map<String, Object> map = new HashMap<>();
     * map.put("username", "怪盗kidou");
     * map.put("age", 24);
     * Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     * <p>
     * 演示 @Multipart 和 @Part
     * MediaType textType = MediaType.parse("text/plain");
     * RequestBody name = RequestBody.create(textType, "怪盗kidou");
     * RequestBody age = RequestBody.create(textType, "24");
     * RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");
     * // 演示 @Multipart 和 @Part
     * MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
     * Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     * <p>
     * 演示 @Multipart 和 @PartMap
     * 实现和上面同样的效果
     * Map<String, RequestBody> fileUpload2Args = new HashMap<>();
     * fileUpload2Args.put("name", name);
     * fileUpload2Args.put("age", age);
     * 这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
     * fileUpload2Args.put("file", file);
     * Call<ResponseBody> call4 = service.testFileUpload2(fileUpload2Args, filePart); //单独处理文件
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    /**
     * 还有一种比较hack的方式可以实现文件上传，
     * 上面说过被当成文件上传的必要条件就是 Content-Disposition 请求头中必须要有 filename="xxx" 才会被当成文件
     * 所有我们在写文件名的时候可以拼把 filename="XXX" 也拼接上去，
     * 即文件名变成  表单键名"; filename="文件名  （两端的引号会自动加，所以这里不加）也可以实现，但是不推荐方式
     *
     * @param args
     * @return Map<String, RequestBody> fileUpload3Args = new HashMap<>();
     * fileUpload3Args.put("name",name);
     * fileUpload3Args.put("age",age);
     * fileUpload3Args.put("file\"; filename=\"test.txt",file);
     * Call<ResponseBody> testFileUpload3 = service.testFileUpload3(fileUpload3Args);
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);

}
