public class SubscriptionProduct extends Product{
    private int renewalPeriodDays;

    public SubscriptionProduct(String name, double price, int quantityInstock, int renewalPeriodDays) {
        super(name, price, quantityInstock);
        this.renewalPeriodDays = renewalPeriodDays;
    }

    @Override 
    public String getShippingInfo(){
        return "No shipping - recurring subscription, renews every " + renewalPeriodDays + " days";
    }

    public int getRenewalPeriodDays() {
        return renewalPeriodDays;
    }
    
}
