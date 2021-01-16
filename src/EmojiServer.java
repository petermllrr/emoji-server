import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class EmojiServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RequestHandler());
        server.start();
    }
}

class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        sendHeaders(exchange);
        sendBody(exchange);
    }

    private void sendHeaders(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, 0);
    }

    private void sendBody(HttpExchange exchange) throws IOException {
        OutputStream response = exchange.getResponseBody();
        String message = Emojis.getRandom() + "\n";
        response.write(message.getBytes());
        response.close();
    }
}

class Emojis {
    private static String[] emojilist = {"🤠", "🚀", "🐭", "🐹", "🐰", "🦊", "🐻",
                                         "🐥", "🐝", "🐌", "🍪", "🔥", "🌟", "🍟",
                                         "🌮", "🍖", "🍕", "🥑", "🍌", "🍊", "🍉"};
    private static int previousEmoji;

    public static String getRandom() {
        int randomEmoji;
        do {
            randomEmoji = new Random().nextInt(emojilist.length);
        } while (randomEmoji == previousEmoji);
        previousEmoji = randomEmoji;
        return emojilist[randomEmoji];
    }
}