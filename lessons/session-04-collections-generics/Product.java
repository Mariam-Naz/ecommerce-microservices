public abstract class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantityInStock;

    public Product(String name, double price, int quantityInStock) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double totalInventoryValue() {
        return price * quantityInStock;
    }

    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    public void sell(int units) {
        quantityInStock = quantityInStock - units;
    }

    public abstract String getShippingInfo();

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return name + " | $" + price + " | stock: " + quantityInStock
                + " | " + getShippingInfo();
    }
}
