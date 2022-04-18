
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.Map;

public interface GrowattWebService {

    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> webLogin(@Field("account") String account, @Field("password") String password,
                                @Field("validateCode") String validateCode);
    
    @POST("tcpSet.do")
    @FormUrlEncoded
    Call<ResponseBody> changeSetting(@FieldMap Map<String, String> fields, @Header("cookie") String cookie);

    @POST("device/getStorageList")
    @FormUrlEncoded
    Call<ResponseBody> getSettings(@FieldMap Map<String, String> fields, @Header("cookie") String cookie);

}
