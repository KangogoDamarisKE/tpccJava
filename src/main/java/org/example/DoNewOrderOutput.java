package org.example;

import java.util.List;

public class DoNewOrderOutput {
    public int w_id;
    public int d_id;
    public int c_id;
    public String c_last;
    public String c_credit;
    public Double c_discount;
    public Double w_tax;
    public Double d_tax;
    public int o_ol_cnt;
    public int o_id;
    public String o_entry_d;
    public Double total_amount;
    List<ItemsData>items;

    public DoNewOrderOutput(){

    }
}
