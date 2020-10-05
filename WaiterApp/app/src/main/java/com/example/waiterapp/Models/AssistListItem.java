package com.example.waiterapp.Models;

/**
 * Object to represent a request for assistance.
 */
public class AssistListItem {
    private String table_no;

    public AssistListItem(String table_no) {
        this.table_no = table_no;
    }

    public String getTable() {
        return this.table_no;
    }
}
