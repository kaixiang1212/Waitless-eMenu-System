package com.example.waiterapp.Models;

import java.util.ArrayList;

/**
 * Object to hold the queue of requests for assistance from tables.
 */
public class AssistList {
    public ArrayList<AssistListItem> assist_list;

    /**
     * Constructor for AssistList.
     */
    public AssistList() {
        assist_list = new ArrayList<>();
    }

    /**
     * Append given AssistListItem to this AssistList
     * @param i - item to append
     */
    public void addAssistListItem(AssistListItem i) {
        assist_list.add(i);
    }

    /**
     * Remove given AssistListItem from this AssistList
     * @param i - item to remove
     */
    public void removeAssistListItem(AssistListItem i) {
        assist_list.remove(i);
    }

    /**
     * Finds an AssistListItem given a particular table number.
     * @param table_no - string representing the table number
     * @return The AssistListItem if found, otherwise null
     */
    public AssistListItem findAssistListItem(String table_no) {
        for (AssistListItem i : assist_list) {
            if (i.getTable().equals(table_no)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Updates this AssistList with another AssistList.
     * Effectively a union set operation, but for AssistLists.
     * @param other - The AssistList to add into this one.
     */
    public void updateAssistList(AssistList other) {
        for (AssistListItem i : other.assist_list) {
            if (this.findAssistListItem(i.getTable()) == null) {
                this.addAssistListItem(i);
            }
        }
    }
}
