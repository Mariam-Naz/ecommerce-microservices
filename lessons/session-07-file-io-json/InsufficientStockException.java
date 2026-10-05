public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String productName, int requested, int available) {
        super("Cannot sell " + requested + " of \"" + productName
                + "\" - only " + available + " in stock");
    }
}
