import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Saves/loads a Catalog to/from a simple text format, one product per
// line, fields separated by commas:
//   PHYSICAL,name,price,quantityInStock,weightKg
//   DIGITAL,name,price,quantityInStock,downloadUrl
//
// This is a hand-rolled, CSV-like format - genuinely how a lot of simple
// data interchange worked for decades, and still worth understanding.
// Real JSON parsing by hand (matching brackets, quotes, escaping special
// characters, nested objects) is a much bigger job than this file's
// length would suggest - which is exactly why almost nobody writes their
// own JSON parser. In Session 8 we set up Maven, which lets us pull in
// the Jackson library with a few lines in a config file, and it will
// convert an entire object graph to/from real JSON automatically. Seeing
// the manual version first is what will make Jackson feel like magic
// instead of a mystery.
public class CatalogFileStorage {

    public void save(Catalog catalog, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product p : catalog.allProducts()) {
                String line;
                if (p instanceof PhysicalProduct) {
                    PhysicalProduct physical = (PhysicalProduct) p;
                    line = "PHYSICAL," + p.getName() + "," + p.getPrice() + ","
                            + p.getQuantityInStock() + "," + physical.getWeightKg();
                } else if (p instanceof DigitalProduct) {
                    DigitalProduct digital = (DigitalProduct) p;
                    line = "DIGITAL," + p.getName() + "," + p.getPrice() + ","
                            + p.getQuantityInStock() + "," + digital.getDownloadUrl();
                }else if (p instanceof SubscriptionProduct) {
                    SubscriptionProduct subscription = (SubscriptionProduct) p;
                    line = "SUBSCRIPTION," + p.getName() + "," + p.getPrice() + ","
                            + p.getQuantityInStock() + "," + subscription.getRenewalPeriodDays();
                } else {
                    // Deliberately not handling every possible subclass
                    // (like SubscriptionProduct, which you wrote yourself
                    // in Session 3) - a real version would need a case
                    // for every type. Note this down as exactly the kind
                    // of maintenance burden that will make Jackson's
                    // reflection-based automatic serialization look very
                    // appealing once we reach it.
                    throw new IllegalArgumentException("Don't know how to save: " + p.getClass());
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save catalog to " + fileName, e);
        }
    }

    public Catalog load(String fileName) {
        Catalog catalog = new Catalog();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantity = Integer.parseInt(parts[3]);

                if (type.equals("PHYSICAL")) {
                    double weightKg = Double.parseDouble(parts[4]);
                    catalog.addProduct(new PhysicalProduct(name, price, quantity, weightKg));
                } else if (type.equals("DIGITAL")) {
                    String downloadUrl = parts[4];
                    catalog.addProduct(new DigitalProduct(name, price, quantity, downloadUrl));
                }else if (type.equals("SUBSCRIPTION")) {
                    int renewalPeriodDays = Integer.parseInt(parts[4]);
                    catalog.addProduct(new SubscriptionProduct(name, price, quantity, renewalPeriodDays));
                } else {
                    throw new IllegalArgumentException("Unknown product type in file: " + type);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load catalog from " + fileName, e);
        }
        return catalog;
    }
}
