import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// A small standalone example before touching the real catalog, so the
// file-handling mechanics are clear on their own first.
public class FileBasicsDemo {

    public static void main(String[] args) {

        String fileName = "notes.txt";

        // --- Writing a file ---
        // "try (...)" here is TRY-WITH-RESOURCES. BufferedWriter holds an
        // open file handle - a real operating system resource that MUST
        // be closed when you're done, or you leak it (the file could stay
        // locked, or your changes might never actually get flushed to
        // disk). try-with-resources guarantees .close() is called
        // automatically at the end of the block, EVEN IF an exception
        // happens inside it. This is the same "always runs" guarantee as
        // "finally" from Session 5, but built specifically for resources
        // that implement the Closeable/AutoCloseable interface.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("First line of the file");
            writer.newLine();
            writer.write("Second line of the file");
            writer.newLine();
            System.out.println("Wrote to " + fileName);
        } catch (IOException e) {
            // IOException is a CHECKED exception (Session 5) - the
            // compiler forces us to handle it, because file operations
            // depend on the outside world (disk full, permissions,
            // the file being open in another program) in ways our own
            // code can't guarantee will succeed.
            System.out.println("Failed to write file: " + e.getMessage());
        }

        // --- Reading a file back, line by line ---
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read: " + line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }

        // --- What happens when the file doesn't exist ---
        try (BufferedReader reader = new BufferedReader(new FileReader("does-not-exist.txt"))) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.out.println("\nExpected failure reading a missing file: " + e.getMessage());
        }
    }
}
