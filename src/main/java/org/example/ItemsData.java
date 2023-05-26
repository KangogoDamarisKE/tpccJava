package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;

@DataType
public class ItemsData {
    int ol_supply_w_id;
    int ol_i_id;
    String i_name;
    int ol_quantity;
    int s_quantity;
    String brand_generic;
    Double i_price;
    Double ol_amount;

    public ItemsData(int ol_supply_w_id, int ol_i_id, String i_name, int ol_quantity, int s_quantity, String brand_generic, 
    Double i_price, Double ol_amount){
        this.ol_supply_w_id = ol_supply_w_id;
        this.ol_i_id = ol_i_id;
        this.i_name = i_name;
        this.ol_quantity = ol_quantity;
        this.s_quantity = s_quantity;
        this.brand_generic = brand_generic;
        this.i_price = i_price;
        this.ol_amount = ol_amount;
    }
    // public ItemsData(){

    // }

}
