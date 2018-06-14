package yannary.retrofit.demo.retrofit;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yannary.retrofit.demo.BaseActivity;
import yannary.retrofit.demo.R;
import yannary.retrofit.demo.retrofit.data.AddressData;
import yannary.retrofit.demo.retrofit.data.Translation;
import yannary.retrofit.demo.retrofit.data.Translation1;
import yannary.retrofit.demo.retrofit.requst.GetRequest_Interface;
import yannary.retrofit.demo.retrofit.requst.PostRequest_Interface;

/**
 * Retrofit 使用示例
 * Created by zhangyanyan on 2018/2/27.
 * 步骤1：添加Retrofit库的依赖
 * 步骤2：创建 接收服务器返回数据 的类 如：Translation
 * 步骤3：创建 用于描述网络请求 的接口 如：GetRequest_Interface
 */

public class RetrofitActivity extends BaseActivity {

    private TextView mResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        mResultTv = (TextView) findViewById(R.id.retrofit_result);
    }

    public void onBackRetrofitClick(View view) {
        mActivity.finish();
    }

    /**
     * Get不带参数
     *
     * @param view
     */
    public void onGetNoParamRetrofitClick(View view) {
        mResultTv.setText("");
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                Translation translation = response.body();
                int status = translation.status;
                Translation.content content = translation.content;
                String from = content.from;
                String to = content.to;
                String vendor = content.vendor;
                String out = content.out;
                int errNo = content.errNo;
                Log.d(TAG, "status:" + status + "\nfrom:" + from + "\nto:" + to
                        + "\nvendor:" + vendor + "\nout:" + out + "\nerrNo:" + errNo);
                mResultTv.setText("status:" + status + "\nfrom:" + from + "\nto:" + to
                        + "\nvendor:" + vendor + "\nout:" + out + "\nerrNo:" + errNo);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onGetNoParamRetrofitClick,error:" + error);
            }
        });
    }

    /**
     * Get带一个参数
     *
     * @param view
     */
    public void onGeSingleParamRetrofitClick(View view) {
        mResultTv.setText("");
        String userId = "72";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/app_jk/zxzc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//        request.getAddress(userId).enqueue(new Callback<AddressData>() {
//            @Override
//            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
//                AddressData addressData = response.body();
//                if (addressData != null) {
//                    String shdz = addressData.getShdz();
//                    mResultTv.setText("收货地址：" + shdz);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddressData> call, Throwable throwable) {
//                String error = throwable.getMessage().toLowerCase().trim();
//                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
//                mResultTv.setText("GetRetrofit,error:" + error);
//            }
//        });
        request.getAddressBody(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    if (TextUtils.isEmpty(result) || result.equals("false")) {
                        mResultTv.setText("result：" + result);
                        return;
                    }
                    mResultTv.setText("result：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onGeSingleParamRetrofitClick,error:" + error);
            }
        });
    }

    /**
     * Post带一个参数
     *
     * @param view
     */
    public void onPostSingleParamRetrofitClick(View view) {
        mResultTv.setText("");
        // 步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation1> call = request.getCall("I love you");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation1>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                Log.d(TAG, response.body().getTranslateResult().get(0).get(0).getTgt());
                mResultTv.setText(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation1> call, Throwable throwable) {
                Log.e(TAG, "请求失败:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onPostSingleParamRetrofitClick,error:" + throwable.getMessage().toString().trim());
            }
        });
    }

    /**
     * Post带多个参数
     *
     * @param view
     */
    public void onPostMultiParamsRetrofitClick(View view) {
        mResultTv.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/app_jk/zxzc/")
                .build();
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);
        Map<String, Object> map = new HashMap();
        map.put("userid", "72");
        map.put("name", "name72");
        map.put("mobilephone", "18677667777");
        map.put("postmark", 455444);
        map.put("shdz", "addresssfssfs");
        request.getAddressBody(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    if (TextUtils.isEmpty(result) || result.equals("false")) {
                        mResultTv.setText("result：" + result);
                        return;
                    }
                    mResultTv.setText("result：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                    mResultTv.setText("result：IOException");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onPostMultiParamsRetrofitClick,error:" + error);
            }
        });
    }

    /**
     * 上传单张图片
     */
    public void onUpLoadSingleImageRetrofitClick(View view) {
        mResultTv.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/app_jk/fetalheart/")
                .build();
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //构建要上传的文件
        File file = new File("/storage/emulated/0/wandoujia/downloader/openscreen/open_screen_bg_img_1513.png");
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "This is a description");
        request.upload(description, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    if (TextUtils.isEmpty(result) || result.equals("false")) {
                        mResultTv.setText("result：" + result);
                        return;
                    }
                    mResultTv.setText("result：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                    mResultTv.setText("result：IOException");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onUpLoadSingleImageRetrofitClick,error:" + error);
            }
        });
    }

    /**
     * 上传多张图片
     * 为了测试方便：存储权限默认开启，图片选取的是我自己手机里的，运行时需更改图片路径
     */
    public void onUpLoadMultiImageRetrofitClick(View view) {
        mResultTv.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/app_jk/fetalheart/")
                .build();
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("/storage/emulated/0/wandoujia/downloader/openscreen/open_screen_bg_img_1513.png"));
        fileList.add(new File("/storage/emulated/0/wandoujia/downloader/openscreen/open_screen_bg_img_1515.png"));
        upLoadMultiImageMethod1(request, fileList);
//        upLoadMultiImageMethod2(request, fileList);
    }

    /**
     * 多文件上传 方法一
     *
     * @param request
     * @param fileList
     */
    private void upLoadMultiImageMethod1(PostRequest_Interface request, List<File> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("description", "This is a method of MultipartBody");
        for (File file : fileList) {
            // TODO: 这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        request.uploadFileWithRequestBody(multipartBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    if (TextUtils.isEmpty(result) || result.equals("false")) {
                        mResultTv.setText("result：" + result);
                        return;
                    }
                    mResultTv.setText("result：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                    mResultTv.setText("result：IOException");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onUpLoadMultiImageRetrofitClick,error:" + error);
            }
        });
    }

    /**
     * 多文件上传 方法二
     *
     * @param request
     * @param fileList
     */
    private void upLoadMultiImageMethod2(PostRequest_Interface request, List<File> fileList) {
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),
                "This is a method of MultipartBody.Part");

        List<MultipartBody.Part> parts = new ArrayList<>(fileList.size());
        for (File file : fileList) {
            // TODO: 这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }

        request.uploadFilesWithParts(description, parts).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    if (TextUtils.isEmpty(result) || result.equals("false")) {
                        mResultTv.setText("result：" + result);
                        return;
                    }
                    mResultTv.setText("result：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                    mResultTv.setText("result：IOException");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String error = throwable.getMessage().toLowerCase().trim();
                Log.e(TAG, "连接失败e:" + throwable.getMessage().toString().trim());
                mResultTv.setText("onUpLoadMultiImageRetrofitClick,error:" + error);
            }
        });
    }
}
