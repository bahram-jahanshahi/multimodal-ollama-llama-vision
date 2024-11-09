package se.bahram.ai.multimodal_ollama_llama_vision;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class MultiModalOllamaLlamaVisionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultiModalOllamaLlamaVisionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var ollamaApi = new OllamaApi("http://localhost:11438");

		var chatModel = new OllamaChatModel(ollamaApi,
				OllamaOptions.create()
						.withModel("llama3.2-vision:latest")
						.withTemperature(0.5));

		/*ChatResponse response = chatModel.call(
				new Prompt("Generate the names of 5 famous philosophers"));*/

		/*Flux<ChatResponse> fluxResponse = chatModel.stream(
				new Prompt("Generate the names of 5 famous philosophers"));
		fluxResponse.subscribe(chatResponse -> {
			System.out.print(chatResponse.getResult().getOutput().getContent());
		});*/

		var imageResource = new ClassPathResource("images/IMG_2409.JPG");

		//describeImage(imageResource, chatModel);

		imageResource = new ClassPathResource("images/maria-romero-p017.jpg");

		describeImage(imageResource, chatModel);
	}

	private static void describeImage(ClassPathResource imageResource, OllamaChatModel chatModel) {
		System.out.println("------------------------------------");
		var userMessage = new UserMessage("Explain what do you see on this picture? express the expression of the person in the picture.",
				new Media(MimeTypeUtils.IMAGE_JPEG, imageResource));

		Flux<ChatResponse> fluxImageResponse = chatModel.stream(
				new Prompt(userMessage));
		fluxImageResponse.subscribe(chatResponse -> {
			System.out.print(chatResponse.getResult().getOutput().getContent());
		});
	}
}
