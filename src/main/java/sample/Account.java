package main.java.sample;

public class Account extends AccountMoney {
    private String iban;
    private boolean isPremium;
    private int daysOverdrawn;
    private Customer customer;

    public Account(boolean isPremium, int daysOverdrawn) {
        super();
        this.isPremium = isPremium;
        this.daysOverdrawn = daysOverdrawn;
    }

    public void withdraw(double sum, String currency) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        setOverdraft(sum);
    }

    private void setOverdraft(double sum) {
        int discount = isPremium ? 2 : 1;
        double factor = customer.getCustomerType() == CustomerType.COMPANY ? customer.getCompanyOverdraftDiscount() / discount : 1;

        if (getMoney() < 0)
            setMoney((getMoney() - sum) - sum * overdraftFee() * factor);
        else setMoney(getMoney() - sum);
    }

    @Override
    public String toString() {
        return "Account:\nIBAN: " + iban + ";\nMoney: " + getMoney() + ";\nAccount Type: " + isPremium;
    }

    public double bankcharge() {
        double result = 4.5;
        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (isPremium) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (isPremium)
            return 0.10;
        else return 0.20;
    }

    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getType() {
        return isPremium ? "Premium" : "Normal";
    }

    public boolean isPremium() {
        return isPremium;
    }

    public String printCustomer() {
        return customer.getName() + " " + customer.getEmail();
    }
    public String printCustomerDaysOverdrawn(Customer customer) {
        String fullName = customer.getFullName();
        String accountDescription = "Account:\nIBAN: " + iban + ";\nDays Overdrawn: " + daysOverdrawn;
        return fullName + accountDescription;
    }
}