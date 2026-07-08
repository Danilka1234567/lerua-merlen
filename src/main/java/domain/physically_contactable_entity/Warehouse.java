package domain.physically_contactable_entity;

import domain.abstract_entities.PhysicallyContactableEntity;

public class Warehouse extends PhysicallyContactableEntity {

    private int capacity;
    private int amountOfProducts;
    private final static int MINIMAL_CAPACITY = 100;

    public Warehouse(String email, String phoneNumber, String name, String address,
                     int capacity, int amountOfProducts) {
        super(email, phoneNumber, name, address);
        setCapacity(capacity);
        setAmountOfProducts(amountOfProducts);
    }

    public Warehouse(Long id, String email, String phoneNumber, String name,
                     String address, int capacity, int amountOfProducts) {
        super(id, email, phoneNumber, name, address);
        setCapacity(capacity);
        setAmountOfProducts(amountOfProducts);
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {

        if (capacity < MINIMAL_CAPACITY)
            throw new IllegalArgumentException(
                    "To work with as warehouse must be able to contain at least %d goods".formatted(
                            MINIMAL_CAPACITY
                    )
            );

        this.capacity = capacity;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {

        if (amountOfProducts < 0)
            throw new IllegalArgumentException(
                    "Can't be less than one good"
            );

        this.amountOfProducts = amountOfProducts;
    }


    public void raiseAmountOfProducts(int amount){
        if (amount <= 0)
            throw new IllegalArgumentException(
                    "Can't add negative amount of goods"
            );

        setAmountOfProducts(amountOfProducts + amount);
    }


    public void decreaseAmountOfProducts(int amount){
        if (amount <= 0)
            throw new IllegalArgumentException(
                    "Decreasable amount must be bigger than 0"
            );

        setAmountOfProducts(amountOfProducts - amount);
    }
}
