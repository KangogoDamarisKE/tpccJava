package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class DoPaymentOutput {
    @Property()
    public int w_id;
    @Property()
    public int d_id;
    @Property()
    public int c_id;
    @Property()
    public int c_d_id;
    @Property()
    public int c_w_id;
    @Property()
    public Double h_amount;
    @Property()
    public String h_date;
    @Property()
    public String w_street_1;
    @Property()
    public String w_street_2;
    @Property()
    public String w_city;
    @Property()
    public String w_state;
    @Property()
    public String w_zip;
    @Property()
    public String d_street_1;
    @Property()
    public String d_street_2;
    @Property()
    public String d_city;
    @Property()
    public String d_state;
    @Property()
    public String d_zip;
    @Property()
    public String c_first;
    @Property()
    public String c_middle;
    @Property()
    public String c_last;
    @Property()
    public String c_street_1;
    @Property()
    public String c_street_2;
    @Property()
    public String c_city;
    @Property()
    public String c_state;
    @Property()
    public String c_zip;
    @Property()
    public String c_phone;
    @Property()
    public String c_since;
    @Property()
    public String c_credit;
    @Property()
    public int c_credit_lim;
    @Property()
    public Double c_discount;
    @Property()
    public Double c_balance;
    @Property()
    public String c_data;

    public DoPaymentOutput(){

    }
}
