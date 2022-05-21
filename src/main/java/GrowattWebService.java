
import model.CurrentStatus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

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

    @POST("panel/storage/getStorageStatusData")
    @FormUrlEncoded
    Call<CurrentStatus> getCurrentStatus(@FieldMap Map<String, String> fields, @Header("cookie") String cookie,
                                         @Query("plantId") String plantId);


}
