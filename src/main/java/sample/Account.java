package main.java.sample;

public class Account {
    private String iban;
    private AccountType type;
    private int daysOverdrawn;
    private double money;
    private String currency;
    private Customer customer;

    public Account(AccountType type, int daysOverdrawn) {
        super();
        this.type = type;
        this.daysOverdrawn = daysOverdrawn;
    }

    public void withdraw(double sum, String currency) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        setOverdraft(sum);
    }

    private void setOverdraft(double sum) {
        int discount = type.isPremium() ? 2 : 1;
        double factor = customer.getCustomerType() == CustomerType.COMPANY ? customer.getCompanyOverdraftDiscount() / discount : 1;

        if (money < 0)
            setMoney((money - sum) - sum * overdraftFee() * factor);
        else setMoney(money - sum);
    }

    @Override
    public String toString() {
        return "Account:\nIBAN: " + iban + ";\nMoney: " + money + ";\nAccount Type: " + type;
    }

    public double bankcharge() {
        double result = 4.5;
        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (type.isPremium()) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (type.isPremium())
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

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getType() {
        return type;
    }

    public String printCustomer() {
        return customer.getName() + " " + customer.getEmail();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String printCustomerDaysOverdrawn(Customer customer) {
        String fullName = customer.getFullName();
        String accountDescription = "Account:\nIBAN: " + iban + ";\nDays Overdrawn: " + daysOverdrawn;
        return fullName + accountDescription;
    }
}