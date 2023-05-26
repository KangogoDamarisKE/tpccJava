package org.example;

import java.util.List;

public class DoDeliveryOutput {
    public int w_id;
    public int o_carrier_id;
    public List<DeliveredOrder> delivered;
    public int skipped;

    public DoDeliveryOutput(int w_id, int o_carrier_id, List<DeliveredOrder> delivered, int skipped){
        this.w_id = w_id;
        this.o_carrier_id = o_carrier_id;
        this.delivered = delivered;
        this.skipped = skipped;
    }
}
