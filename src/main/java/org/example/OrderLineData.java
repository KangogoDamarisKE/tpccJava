package org.example;

import java.util.Date;

public class OrderLineData {
    public int ol_supply_w_id;
    public int ol_i_id;
    public int ol_quantity;
    public Double ol_amount;
    public Date ol_delivery_d;

    public OrderLineData(int ol_supply_w_id, int ol_i_id, int ol_quantity, Double ol_amount, Date ol_delivery_d){
        this.ol_supply_w_id= ol_supply_w_id;
        this.ol_i_id= ol_i_id;
        this.ol_quantity= ol_quantity;
        this.ol_amount= ol_amount;
        this.ol_delivery_d= ol_delivery_d;
    }
}
