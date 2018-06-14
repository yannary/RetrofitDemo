package yannary.retrofit.demo.retrofit.requst;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yannary.retrofit.demo.retrofit.data.AddressData;
import yannary.retrofit.demo.retrofit.data.Translation;

/**
 * Created by zhangyanyan on 2018/2/27.
 */

public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();

    /**
     * 参数多时用（@QueryMap Map<String, String> params）
     * @param userid
     * @return
     */
    @GET("cx_shdz.jsp")
    Call<AddressData> getAddress(@Query("userid") String userid);

    @GET("cx_shdz.jsp")
    Call<ResponseBody> getAddressBody(@Query("userid") String userid);

}
