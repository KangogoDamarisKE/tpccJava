package org.example;

import java.util.List;

public class DoOrderStatusOutput {
    public int w_id;
    public int d_id;
    public int c_id;
    public String c_first;
    public String c_middle;
    public String c_last;
    public Double c_balance;
    public int o_id;
    public String o_entry_d;
    public int o_carrier_id;
    List<OrderLineData>order_lines;

    public DoOrderStatusOutput(){
        
    }
}
