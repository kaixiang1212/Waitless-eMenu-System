package com.example.waiterapp.Models;

/**
 * POJO to store each parsed OrderItem from JSON
 */
public class OrderItemJSON {
    private String _id;
    private String item;
    private String quantity;
    private String table_no;
    private float timestamp;
    private String status;

    // Getter Methods
    public String get_id() {
        return _id;
    }

    public String getItem() {
        return item;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTable_no() {
        return table_no;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods
    public void set_id(String _id) {
        this._id = _id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTable_no(String table_no) {
        this.table_no = table_no;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
