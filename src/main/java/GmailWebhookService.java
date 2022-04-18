
import model.WebhookBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GmailWebhookService {

    @Headers("Content-Type: application/json")
    @POST(Configurations.googleChatWebhookUrl)
    Call<ResponseBody> alert(@Body WebhookBody body, @Query("thread_key") String thread_key);

}
