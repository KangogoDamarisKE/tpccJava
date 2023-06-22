package org.example;

import java.util.List;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class DoOrderStatusOutput {
    @Property()
    public int w_id;

    @Property()
    public int d_id;

    @Property()
    public int c_id;

    @Property()
    public String c_first;

    @Property()
    public String c_middle;

    @Property()
    public String c_last;

    @Property()
    public Double c_balance;

    @Property()
    public int o_id;

    @Property()
    public String o_entry_d;

    @Property()
    public int o_carrier_id;
    
    @Property()
    List<OrderLineData>order_lines;

    public DoOrderStatusOutput(){
        
    }
}
