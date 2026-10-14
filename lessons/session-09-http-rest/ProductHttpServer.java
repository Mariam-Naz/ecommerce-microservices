import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

// A real, working HTTP server using only the JDK - no Spring, no Maven,
// no external library at all. com.sun.net.httpserver has shipped inside
// the JDK for years specifically for small cases like this. Nobody builds
// production services this way (Spring Boot, next session, does
// everything here automatically and far more robustly) - but writing the
// raw version once is what makes Spring Boot's annotations later feel
// like "oh, THAT'S what that's automating" instead of unexplained magic.
public class ProductHttpServer {

    public static void main(String[] args) throws IOException {

        Catalog catalog = new Catalog();
        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));

        // A server SOCKET listens on a port - think of a port as one of
        // thousands of numbered "doors" on your machine's network address.
        // 8080 is a common convention for local development HTTP servers
        // (80 is the real default HTTP port, but using it usually needs
        // admin/root privileges, so developers use 8080 instead).
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // A "context" maps a URL PATH to a HANDLER - the code that runs
        // when a request arrives for that path. This single line is
        // doing the job that an entire @RestController class will do
        // automatically in Spring Boot next session.
        server.createContext("/products", exchange -> handleProducts(exchange, catalog));
        server.createContext("/health", exchange -> handleHealth(exchange));

        server.start();
        System.out.println("Server listening on http://localhost:8080/products");
        System.out.println("Open that URL in a browser, or run: curl http://localhost:8080/products");
        System.out.println("Press Ctrl+C to stop the server.");
    }

    private static void handleProducts(HttpExchange exchange, Catalog catalog) throws IOException {

        // Every HTTP request has a METHOD - GET (read data), POST (create),
        // PUT (replace), PATCH (partially update), DELETE (remove). This
        // is the core of REST: operations are expressed through the HTTP
        // method itself, not through the URL ("/getProducts" would be the
        // WRONG, non-RESTful way to design this).
        String method = exchange.getRequestMethod();

        // The URL PATH identifies WHICH resource. "/products" means "the
        // collection of products". A real product-service would also
        // handle "/products/Wireless%20Mouse" for ONE specific product -
        // the path identifies the resource, not the action.
        String path = exchange.getRequestURI().getPath();

        System.out.println("Received: " + method + " " + path);

        if (!method.equals("GET")) {
            // 405 Method Not Allowed - a real HTTP STATUS CODE. Status
            // codes are a standardized way for a server to tell the
            // client, in a single number, what happened:
            //   2xx = success (200 OK, 201 Created, 204 No Content)
            //   4xx = the CLIENT made a mistake (400 Bad Request,
            //         404 Not Found, 405 Method Not Allowed)
            //   5xx = the SERVER failed (500 Internal Server Error)
            sendResponse(exchange, 405, "{\"error\":\"Method not allowed, use GET\"}");
            return;
        }

        String body = catalog.toJsonArray();

        // The RESPONSE also has structure: a status code, HEADERS (meta-
        // information about the response - here, telling the client
        // "this body is JSON text" via Content-Type), and a BODY (the
        // actual data). Try removing the Content-Type header and
        // reloading in a browser - it'll likely still show the text, but
        // tools that expect JSON (like a frontend app) would mishandle it.
        sendResponse(exchange, 200, body);
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 200, "{\"status\":\"ok\"}");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        // exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
