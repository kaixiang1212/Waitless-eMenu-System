package com.example.customerapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This object represents the bill for the table and contains the items in the Bill
 */
public class Bill {

    private List<BillItem> bill_items;

    /**
     * Public Constructor
     */
    public Bill() {
        bill_items = new ArrayList<BillItem>();
    }

    /**
     * Gets the items in the bill
     * @return bill_items : items in the bill
     */
    public List<BillItem> getItems() {
        return bill_items;
    }

}
