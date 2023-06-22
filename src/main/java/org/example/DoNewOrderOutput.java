package org.example;

import java.util.List;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

//import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class DoNewOrderOutput {
    @Property()
    public final int w_id;
    @Property()
    public final int d_id;
    @Property()
    public final int c_id;
    @Property()
    public final String c_last;
    @Property()
    public final String c_credit;
    @Property()
    public final Double c_discount;
    @Property()
    public final Double w_tax;
    @Property()
    public final Double d_tax;
    @Property()
    public final int o_ol_cnt;
    @Property()
    public final int o_id;
    @Property()
    public final String o_entry_d;
    @Property()
    public final Double total_amount;
    @Property()
    public final List<ItemsData> items;

    // public DoNewOrderOutput(
    //         @JsonProperty("w_id") final int w_id,
    //         @JsonProperty("d_id") final int d_id,
    //         @JsonProperty("c_id") final int c_id,
    //         @JsonProperty("c_last") final String c_last,
    //         @JsonProperty("c_credit") final String c_credit,
    //         @JsonProperty("c_discount") final Double c_discount,
    //         @JsonProperty("w_tax") final Double w_tax,
    //         @JsonProperty("d_tax") final Double d_tax,
    //         @JsonProperty("o_ol_cnt") final int o_ol_cnt,
    //         @JsonProperty("o_id") final int o_id,
    //         @JsonProperty("o_entry_d") final String o_entry_d,
    //         @JsonProperty("total_amount") final Double total_amount,
    //         @JsonProperty("items") final List<ItemsData> items) {

    //     this.w_id = w_id;
    //     this.d_id = d_id;
    //     this.c_id = c_id;
    //     this.c_last = c_last;
    //     this.c_credit = c_credit;
    //     this.c_discount = c_discount;
    //     this.w_tax = w_tax;
    //     this.d_tax = d_tax;
    //     this.o_ol_cnt = o_ol_cnt;
    //     this.o_id = o_id;
    //     this.o_entry_d = o_entry_d;
    //     this.total_amount = total_amount;
    //     this.items = items;
    // }
    public DoNewOrderOutput(int w_id, int d_id, int c_id, String c_last,String c_credit, 
    Double c_discount, Double w_tax, Double d_tax, int o_ol_cnt, int o_id, String o_entry_d, 
    Double total_amount, List<ItemsData> items) {

    this.w_id = w_id;
    this.d_id = d_id;
    this.c_id = c_id;
    this.c_last = c_last;
    this.c_credit = c_credit;
    this.c_discount = c_discount;
    this.w_tax = w_tax;
    this.d_tax = d_tax;
    this.o_ol_cnt = o_ol_cnt;
    this.o_id = o_id;
    this.o_entry_d = o_entry_d;
    this.total_amount = total_amount;
    this.items = items;
}
}
