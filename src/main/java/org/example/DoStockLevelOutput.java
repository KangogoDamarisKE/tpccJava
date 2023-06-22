package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class DoStockLevelOutput {
    @Property()
    public int w_id;
    @Property()
    public int d_id;
    @Property()
    public int threshold;
    @Property()
    public int low_stock;


    public DoStockLevelOutput(){
        
    }
}
