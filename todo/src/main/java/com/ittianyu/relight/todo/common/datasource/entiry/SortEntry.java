package com.ittianyu.relight.todo.common.datasource.entiry;

public class SortEntry {
    public static final int ORDER_ASC = 0;
    public static final int ORDER_DESC = 1;

    private String field;
    private int order;

    public static int getOrderAsc() {
        return ORDER_ASC;
    }

    public static int getOrderDesc() {
        return ORDER_DESC;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SortEntry{" +
            "field='" + field + '\'' +
            ", order=" + order +
            '}';
    }
}
