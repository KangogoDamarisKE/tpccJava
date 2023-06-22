package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

//import java.util.Date;

@DataType()
public class OrderLineData {
    @Property()
    public int ol_supply_w_id;
    @Property()
    public int ol_i_id;
    @Property()
    public int ol_quantity;
    @Property()
    public Double ol_amount;
    @Property()
    public String ol_delivery_d;

    public OrderLineData(){
        
    }

    // public OrderLineData(int ol_supply_w_id, int ol_i_id, int ol_quantity, Double ol_amount, String ol_delivery_d){
    //     this.ol_supply_w_id= ol_supply_w_id;
    //     this.ol_i_id= ol_i_id;
    //     this.ol_quantity= ol_quantity;
    //     this.ol_amount= ol_amount;
    //     this.ol_delivery_d= ol_delivery_d;
    // }
}
