import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputText;


public class LeaderBoard {

    private static final String CHAT_GPT_KEY = "";
    private static final String ADVENT_OF_CODE_SESSION_COOKIE = "";
    private static final String SLACK_WEBHOOK = "";

    public static String fetchJsonWithSessionCookie(String urlString, String sessionCookieValue) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Cookie", "session=" + sessionCookieValue)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + response.statusCode());
        }

        return response.body();
    }

    public static void slackMessage(String urlString, String message) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"message\": \"" + message + "\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + response.statusCode());
        }
    }

    public static void main(String[] args) throws Exception {

        String json = fetchJsonWithSessionCookie("https://adventofcode.com/2025/leaderboard/private/view/2292080.json", ADVENT_OF_CODE_SESSION_COOKIE);

        OpenAIClient client = OpenAIOkHttpClient.builder().apiKey(CHAT_GPT_KEY).build();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .input("""
                        Summarize this JSON leaderboard in a beautified human readable way. 
                        Don't show star timestamps. Don't show player IDs. Just show each player's position on the leaderboard, their number of stars and score. 
                        Order the list in descending order by score.
                        Exclude players that have 0 score. 
                        Include some fun emojis. Include star emojis with the star count - a star emoji for each star earned. 
                        The competition is called "Advent of Code" and is a Christmas themed coding challenge. It started on 1 December 2025 and is 12 days long.
                        Make some jokes. Use snarky and sarcastic humour.""" + json)
                .model("gpt-5-nano")
                .build();

        Response response = client.responses().create(params);
        String m = response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(ResponseOutputText::text)
                .collect(Collectors.joining());

        System.out.println(m);

        slackMessage(SLACK_WEBHOOK, m);

    }

}
