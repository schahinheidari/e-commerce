package org.example.shoppingbackend.enums;

import java.util.Arrays;

/**
 * Represents the lifecycle stages of an order in the e-commerce system.
 * Each status has a unique code and descriptive explanation of the order state.
 */
public enum OrderStatus {
    /**
     * Order has been placed but payment and inventory verification are pending.
     * This is the initial state of all new orders.
     */
    PENDING(1, "Order received but not yet processed"),

    /**
     * Order is being prepared for shipment.
     * Includes payment verification, inventory allocation, and packaging.
     */
    PROCESSING(2, "Order is being prepared for shipment"),

    /**
     * Order has been handed over to the delivery carrier.
     * Includes tracking information generation and dispatch.
     */
    SHIPPED(3, "Order has left our warehouse and is in transit"),

    /**
     * Order has been successfully delivered to the customer.
     * Final state for completed orders.
     */
    DELIVERED(4, "Order has been delivered to the customer"),

    /**
     * Order has been cancelled either by customer or system.
     * Could be due to payment failure, inventory issues, or customer request.
     */
    CANCELLED(5, "Order has been cancelled");

    private final int code;
    private final String desc;

    /**
     * Constructs an OrderStatus enum constant.
     *
     * @param code the unique numerical identifier for the status
     * @param desc the human-readable description of the status
     */
    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * Retrieves the numerical code representing this order status.
     *
     * @return the status code as integer
     */
    public int getCode() {
        return code;
    }

    /**
     * Retrieves the descriptive explanation of this order status.
     *
     * @return the status description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Returns the status description as the string representation.
     * This is particularly useful for logging and UI display purposes.
     *
     * @return the status description
     */
    @Override
    public String toString() {
        return desc;
    }

    /**
     * Finds an OrderStatus by its numerical code.
     *
     * @param code the status code to search for
     * @return the matching OrderStatus
     * @throws IllegalArgumentException if no matching status is found
     */
    public static OrderStatus fromCode(int code) {
        return Arrays.stream(values())
                .filter(status -> status.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OrderStatus code: " + code));
    }
}
