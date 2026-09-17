import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FilePurpose;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputContent;
import com.openai.models.responses.ResponseInputImage;
import com.openai.models.responses.ResponseInputItem;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Llm {
    OpenAIClient client;
    List<String> files = new ArrayList<>();

    Llm(List<String> files) {
        this.client = OpenAIOkHttpClient.fromEnv();
        this.files = files;

        //todo: wie funktioniert das genau mit List <-> ArrayList, weshalb konnte ich es in der Main.java nicht machen?
        List<ResponseInputContent> images = new ArrayList<>();

        //todo: sauberer Weg, das zu formatieren (code style)?
        //todo: evtl. auftrennen / lesbarer machen
        for (String file : files) {
            images.add(
                    ResponseInputContent.ofInputImage(
                            ResponseInputImage.builder().detail(
                                    ResponseInputImage.Detail.AUTO).fileId(
                                            client.files().create(
                                                    FileCreateParams.builder().file(Paths.get(file)).purpose(FilePurpose.VISION).build())
                                                    .id())
                                    .build()));
        }

        var response = client.responses().create(
                ResponseCreateParams.builder().model("gpt-4o-mini").inputOfResponse(
                        List.of(
                                ResponseInputItem.ofMessage(
                                        ResponseInputItem.Message.builder().role(
                                                ResponseInputItem.Message.Role.USER).addInputTextContent("What's in this image?").content(images).build()))).build());

        response.output()
                .stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .forEach(text -> System.out.println(text.text()));
    }


}