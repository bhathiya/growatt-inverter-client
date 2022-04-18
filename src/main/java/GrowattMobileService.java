
import model.CurrentStatus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GrowattMobileService {

    @FormUrlEncoded
    @POST("newLoginAPI.do")
    Call<ResponseBody> mobileLogin(@Field("userName") String username, @Field("password") byte[] password);

    @FormUrlEncoded
    @POST("newStorageAPI.do?op=getSystemStatusData")
    Call<CurrentStatus> getCurrentStatus(@Query("plantId") String plantId, @Field("storageSn") String serialNumber,
                                         @Header("cookie") String cookie);

}
