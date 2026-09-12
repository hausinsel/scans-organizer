import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FilePurpose;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputImage;
import com.openai.models.responses.ResponseInputItem;

import java.nio.file.Paths;
import java.util.List;

public class Llm {
    OpenAIClient client;

    Llm() {
        this.client = OpenAIOkHttpClient.fromEnv();

        var file =
                client
                        .files()
                        .create(
                                FileCreateParams.builder()
                                        .file(Paths.get("C:\\Users\\cmouy\\Pictures\\Scans\\angstgespraech_1.png"))
                                        .purpose(FilePurpose.VISION)
                                        .build());

        var file2 =
                client
                        .files()
                        .create(
                                FileCreateParams.builder()
                                        .file(Paths.get("C:\\Users\\cmouy\\Pictures\\Scans\\angstgespraech_2.png"))
                                        .purpose(FilePurpose.VISION)
                                        .build());

        var response =
                client.responses()
                        .create(
                                ResponseCreateParams.builder()
                                        .model("gpt-4o-mini")
                                        .inputOfResponse(
                                                List.of(
                                                        ResponseInputItem.ofMessage(
                                                                ResponseInputItem.Message.builder()
                                                                        .role(ResponseInputItem.Message.Role.USER)
                                                                        .addInputTextContent("What's in this image?")
                                                                        .addContent(ResponseInputImage.builder().detail(ResponseInputImage.Detail.AUTO).fileId(file.id()).build())
                                                                        .addContent(ResponseInputImage.builder().detail(ResponseInputImage.Detail.AUTO).fileId(file2.id()).build())
                                                                        .build())))
                                        .build());

        response.output().stream().flatMap(item -> item.message().stream()).flatMap(message -> message.content().stream()).flatMap(content -> content.outputText().stream()).forEach(text -> System.out.println(text.text()));
    }


}