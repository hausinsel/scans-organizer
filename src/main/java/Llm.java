import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class Llm {
    OpenAIClient client;

    Llm() {
        //ToDo: Fehlerabfang einbauen?
        this.client = OpenAIOkHttpClient.fromEnv();
    }


}