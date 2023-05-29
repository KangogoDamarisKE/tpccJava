/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http =//www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
SPDX-License-Identifier = Apache-2.0
*/

package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.json.JSONArray;
import org.json.JSONObject;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;


@Contract(name = "TPCC",
    info = @Info(title = "tpcc contract",
                description = "My Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "tpcc@example.com",
                                                name = "tpcc",
                                                url = "http://tpcc.me")))

/**
 * The implementation of the TPC-C benchmark smart contract according to the specification version v5.11.0.
 */

@Default
public class TPCC implements ContractInterface {


    public  TPCC() {

    }
    // public void addTxInfo(Context ctx) {
    //     ctx.getTxInfo().tx_id = ctx.getStub().getTxId();
    //     ctx.getTxInfo().cc_start_time_epoch_ms = System.currentTimeMillis();
    //     ctx.getTxInfo().cc_end_time_epoch_ms = 0;
    //     ctx.getTxInfo().cc_duration_ms = 0;
    //     ctx.getTxInfo().stat_get_cnt = 0;
    //     ctx.getTxInfo().stat_get_size_bytes = 0;
    //     ctx.getTxInfo().stat_get_exec_total_ms = 0;
    //     ctx.getTxInfo().stat_put_cnt = 0;
    //     ctx.getTxInfo().stat_put_size_bytes = 0;
    //     ctx.getTxInfo().stat_put_exec_total_ms = 0;
    //     ctx.getTxInfo().stat_del_cnt = 0;
    //     ctx.getTxInfo().stat_del_exec_total_ms = 0;
    //     ctx.getTxInfo().stat_iterate_cnt = 0;
    //     ctx.getTxInfo().stat_iterate_size_bytes = 0;
    //     ctx.getTxInfo().stat_iterate_exec_total_ms = 0;
    //     ctx.getTxInfo().md_tpcc_delivery_skipped = 0;
    //     ctx.getTxInfo().md_tpcc_delivery_total_order_lines = 0;
    //     ctx.getTxInfo().md_tpcc_order_status_order_lines = 0;
    //     ctx.getTxInfo().md_tpcc_stock_level_recent_items = 0;
    // }
    
    // public static void finishTx(Context ctx) {
    //     ctx.getTxInfo().cc_end_time_epoch_ms = new Date().getTime();
    //     ctx.getTxInfo().cc_duration_ms = ctx.getTxInfo().cc_end_time_epoch_ms - ctx.getTxInfo().cc_start_time_epoch_ms;
    
    //     System.out.println(new Date().toInstant().toString() + " | info | txinfo | " + JSON.stringify(ctx.getTxInfo()));
    // }

    @Transaction
     /**
     * Creates a new entry during the loading phase of the benchmark.
     * @param ctx The TX context.
     * @param parameters The JSON encoded array of entries.
     * @
     */
    public static void createEntries(Context ctx, String parameters) {
        //addTxInfo(ctx);        

        JSONObject params = new JSONObject(parameters);
        
        JSONArray entryList = params.getJSONArray("entries");

        //log("Starting Create Entries TX for " + entryList.length + "entries", ctx, "info");

        try {
            for (int i = 0; i < entryList.length(); i++) {
                JSONObject entry = entryList.getJSONObject(i);

                switch (entry.getString("table")) {
                    case common.TABLES.WAREHOUSE:
                        LedgerUtils.createWarehouse(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.DISTRICT:
                        LedgerUtils.createDistrict(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.CUSTOMER:
                        LedgerUtils.createCustomer(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.HISTORY:
                        LedgerUtils.createHistory(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.NEW_ORDER:
                        LedgerUtils.createNewOrder(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.ORDERS:
                        LedgerUtils.createOrder(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.ORDER_LINE:
                        LedgerUtils.createOrderLine(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.ITEM:
                        LedgerUtils.createItem(ctx, entry.getString("data"));
                        break;
                    case common.TABLES.STOCK:
                        LedgerUtils.createStock(ctx, entry.getString("data"));
                        break;
                    default:
                        throw new Exception("Unknown record type: " + entry.getString("table"));
                }
            }

            // log('Finished Create Entries TX', ctx, 'info');
        } 
        catch (Exception err) {
            common.log(err.toString(), ctx, "error");
        } 
    }

    @Transaction
    /**
     * Performs the Delivery read-write TX profile.
     * @param ctx The TX context.
     * @param parameters The JSON encoded parameters of the TX profile.
     * @return The JSON encoded query results according to the specification.
     */
    public static DoDeliveryOutput doDelivery(Context ctx, String parameters) {
        //addTxInfo(ctx);

        // TPC-C 2.7.4.2
        // log(`Starting Delivery TX with parameters: ${parameters}`, ctx, 'info');
        try {
            final DeliveryParameters params = ParseUtils.parseDeliveryParameters(parameters);

            // For a given warehouse number (W_ID), for each of the 10 districts (D_W_ID , D_ID)
            // within that warehouse, and for a given carrier number (O_CARRIER_ID):
            
            int skipped = 0;

            List<DeliveredOrder> deliveredOrders = new ArrayList<>();

            for (int d_id = 1; d_id <= 10; d_id++) {
                // The row in the NEW-ORDER table with matching NO_W_ID (equals W_ID) and NO_D_ID
                // (equals D_ID) and with the lowest NO_O_ID value is selected. This is the oldest
                // undelivered order of that district. NO_O_ID, the order number, is retrieved.
                NewOrder newOrder = LedgerUtils.getOldestNewOrder(ctx, params.w_id, d_id);
                if (newOrder == null) {
                    // If no matching row is found, then the delivery of an order for this district
                    // is skipped. The condition in which no outstanding order is present at a given
                    // district must be handled by skipping the delivery of an order for that district
                    // only and resuming the delivery of an order from all remaining districts of the
                    // selected warehouse. If this condition occurs in more than 1%, or in more than one,
                    // whichever is greater, of the business transactions, it must be reported.
                    common.log("Could not find new order for District( " + params.w_id + "," + d_id + " ) skipping it" , ctx, "info");
                    skipped += 1;
                    continue;
                }

                LedgerUtils.deleteNewOrder(ctx, newOrder);

                // The row in the ORDER table with matching O_W_ID (equals W_ ID), O_D_ID (equals D_ID),
                // and O_ID (equals NO_O_ID) is selected, O_C_ID, the customer number, is retrieved,
                // and O_CARRIER_ID is updated.
                Order order = LedgerUtils.getOrder(ctx, params.w_id, d_id, newOrder.no_o_id);
                order.o_carrier_id = params.o_carrier_id;
                LedgerUtils.updateOrder(ctx, order);

                // All rows in the ORDER-LINE table with matching OL_W_ID (equals O_W_ID), OL_D_ID
                // (equals O_D_ID), and OL_O_ID (equals O_ID) are selected. All OL_DELIVERY_D, the
                // delivery dates, are updated to the current system time as returned by the operating
                // system and the sum of all OL_AMOUNT is retrieved.
                Double orderLineAmountTotal = 0.0;
                //ctx.getTxinfo().md_tpcc_delivery_total_order_lines += order.o_ol_cnt;

                for (int i = 1; i <= order.o_ol_cnt; i++) {
                    OrderLine orderLine = LedgerUtils.getOrderLine(ctx, params.w_id, d_id, order.o_id, i);
                    orderLineAmountTotal += orderLine.ol_amount;
                    orderLine.ol_delivery_d = params.ol_delivery_d;

                    LedgerUtils.updateOrderLine(ctx, orderLine);
                }

                // The row in the CUSTOMER table with matching C_W_ID (equals W_ID), C_D_ID
                // (equals D_ID), and C_ID (equals O_C_ID) is selected and C_BALANCE is increased by
                // the sum of all order-line amounts (OL_AMOUNT) previously retrieved. C_DELIVERY_CNT
                // is incremented by 1.
                Customer customer = LedgerUtils.getCustomer(ctx, params.w_id, d_id, order.o_c_id);
                customer.c_balance += orderLineAmountTotal;
                customer.c_delivery_cnt += 1;
                LedgerUtils.updateCustomer(ctx, customer);

                

                deliveredOrders.add(new DeliveredOrder(d_id, order.o_id));

            }

            DoDeliveryOutput output = new DoDeliveryOutput(params.w_id, params.o_carrier_id, deliveredOrders, skipped);


            return output;
        } 
        catch (Exception err) {
            common.log(err.toString(), ctx, "error");
        }
        return null;
    }
    
    @Transaction
    /**
     * Performs the New Order read-write TX profile.
     * @param ctx The TX context.
     * @param parameters The JSON encoded parameters of the TX profile.
     * @return {Promise<{object}>} The JSON encoded query results according to the specification.
     */
    public static DoNewOrderOutput doNewOrder(Context ctx, String parameters) {
        //addTxInfo(ctx);

        // TPC-C 2.4.2.2
        // log(`Starting New Order TX with parameters: ${parameters}`, ctx, 'info');
        try {
            final NewOrderParameters params = ParseUtils.parseNewOrderParameters(parameters);

            // The row in the WAREHOUSE table with matching W_ID is selected and W_TAX,
            // the warehouse tax rate, is retrieved.
            final Warehouse warehouse = LedgerUtils.getWarehouse(ctx, params.w_id);

            // The row in the DISTRICT table with matching D_W_ID and D_ ID is selected,
            // D_TAX, the district tax rate, is retrieved, and D_NEXT_O_ID, the next
            // available order number for the district, is retrieved and incremented by one.
            District district = LedgerUtils.getDistrict(ctx, warehouse.w_id, params.d_id);
            int nextOrderId = district.d_next_o_id;
            district.d_next_o_id += 1;
            LedgerUtils.updateDistrict(ctx, district);

            // The row in the CUSTOMER table with matching C_W_ID, C_D_ID, and C_ID is
            // selected and C_DISCOUNT, the customer's discount rate, C_LAST, the customer's
            // last name, and C_CREDIT, the customer's credit status, are retrieved.
            final Customer customer = LedgerUtils.getCustomer(ctx, warehouse.w_id, district.d_id, params.c_id);

            // A new row is inserted into both the NEW-ORDER table and the ORDER table to
            // reflect the creation of the new order. O_CARRIER_ID is set to a null value.
            // If the order includes only home order-lines, then O_ALL_LOCAL is set to 1,
            // otherwise O_ALL_LOCAL is set to 0.

            final NewOrder  newOrder = new NewOrder(); 
                newOrder.no_o_id = nextOrderId;
                newOrder.no_d_id = district.d_id;
                newOrder.no_w_id = warehouse.w_id;
            
            LedgerUtils.createNewOrder(ctx, newOrder.toString());

            boolean allItemsLocal = Arrays.stream(params.i_w_ids).allMatch(i_w_id -> i_w_id == warehouse.w_id);
                        
                final Order order = new Order();
                order.o_id = nextOrderId;
                order.o_d_id = district.d_id;
                order.o_w_id = warehouse.w_id;
                order.o_c_id = customer.c_id;
                order.o_entry_d = params.o_entry_d;
                order.o_carrier_id = 0;
                order.o_ol_cnt = params.i_ids.length;
                order.o_all_local = allItemsLocal ? 1 : 0;
            
            LedgerUtils.createOrder(ctx, order.toString());

            List<ItemsData> itemsData = new ArrayList<>();

            Double totalOrderLineAmount = 0.0;
            // For each O_OL_CNT item on the order
            for (int i = 0; i < params.i_ids.length; i++) {
                int i_id = params.i_ids[i];
                int i_w_id = params.i_w_ids[i];
                int i_qty = params.i_qtys[i];

                // The row in the ITEM table with matching I_ID (equals OL_I_ID) is selected
                // and I_PRICE, the price of the item, I_NAME, the name of the item, and
                // I_DATA are retrieved. If I_ID has an unused value (see Clause 2.4.1.5), a
                // "not-found" condition is signaled, resulting in a rollback of the
                // database transaction (see Clause 2.4.2.3).

                Item item = LedgerUtils.getItem(ctx, i_id);
                if (item == null) {
                    // 2.4.3.4 For transactions that are rolled back as a result of an
                    // unused item number (1% of all New-Order transactions), the emulated
                    // terminal must display in the appropriate fields of the input/output
                    // screen the fields: W_ID, D_ID, C_ID, C_LAST, C_CREDIT, O_ID, and the
                    // execution status message "Item number is not valid".
                    
                    HashMap<String, Object> errorOutput = new HashMap<>();
                    errorOutput.put("w_id", warehouse.w_id);
                    errorOutput.put("d_id", district.d_id);
                    errorOutput.put("c_id", customer.c_id);
                    errorOutput.put("c_last", customer.c_last);
                    errorOutput.put("c_credit", customer.c_credit);
                    errorOutput.put("o_id", order.o_id);
                    errorOutput.put("message", "Item number is not valid");
                    
                    // ErrorOutput errorOutput = new ErrorOutput();
                    // errorOutput.w_id = warehouse.w_id;
                    // errorOutput.d_id = district.d_id;
                    // errorOutput.c_id= customer.c_id;
                    // errorOutput.c_last= customer.c_last;
                    // errorOutput.c_credit= customer.c_credit;
                    // errorOutput.o_id= order.o_id;
                    // errorOutput.message= "Item number is not valid'";

                    common.log("Item not found. Error output:" + errorOutput , ctx, "info");
                    throw new Exception(new JSONObject(errorOutput).toString());
                }

                // The row in the STOCK table with matching S_I_ID (equals OL_I_ID) and
                // S_W_ID (equals OL_SUPPLY_W_ID) is selected. S_QUANTITY, the quantity in
                // stock, S_DIST_xx, where xx represents the district number, and S_DATA are
                // retrieved. If the retrieved value for S_QUANTITY exceeds OL_QUANTITY by
                // 10 or more, then S_QUANTITY is decreased by OL_QUANTITY; otherwise
                // S_QUANTITY is updated to (S_QUANTITY - OL_QUANTITY)+91. S_YTD is
                // increased by OL_QUANTITY and S_ORDER_CNT is incremented by 1. If the
                // order-line is remote, then S_REMOTE_CNT is incremented by 1.
                Stock stock = LedgerUtils.getStock(ctx, i_w_id, i_id);

                if (stock.s_quantity >= i_qty + 10) {
                    stock.s_quantity -= i_qty;
                } 
                else {
                    stock.s_quantity = (stock.s_quantity - i_qty) + 91;
                }

                stock.s_ytd += i_qty;
                stock.s_order_cnt += 1;

                if (i_w_id != warehouse.w_id) {
                    stock.s_remote_cnt += 1;
                }

                LedgerUtils.updateStock(ctx, stock);

                // The amount for the item in the order (OL_AMOUNT) is computed as:
                // OL_QUANTITY * I_PRICE
                Double orderLineAmount = i_qty * item.i_price;
                totalOrderLineAmount += orderLineAmount;

                // The strings in I_DATA and S_DATA are examined. If they both include the
                // string "ORIGINAL", the brand-generic field for that item is set to "B",
                // otherwise, the brand-generic field is set to "G".
                String brandGeneric;
                if (item.i_data.contains("ORIGINAL") && stock.s_data.contains("ORIGINAL")) {
                    brandGeneric = "B";
                } else {
                    brandGeneric = "G";
                }

                // A new row is inserted into the ORDER-LINE table to reflect the item on
                // the order. OL_DELIVERY_D is set to a null value, OL_NUMBER is set to a
                // unique value within all the ORDER-LINE rows that have the same OL_O_ID
                // value, and OL_DIST_INFO is set to the content of S_DIST_xx, where xx
                // represents the district number (OL_D_ID)
                String stockDistrictId = String.format("%02d", district.d_id);
                
                //final OrderLine orderLine = new OrderLine(nextOrderId, district.d_id, warehouse.w_id, i + 1, i_id, i_w_id, null, i_qty, orderLineAmount, "s_dist_" + stockDistrictId);
                final OrderLine orderLine = new OrderLine();
                orderLine.ol_o_id = nextOrderId;
                orderLine.ol_d_id = district.d_id;
                orderLine.ol_w_id = warehouse.w_id;
                orderLine.ol_number = i + 1;
                orderLine.ol_i_id = i_id;
                orderLine.ol_supply_w_id = i_w_id;
                orderLine.ol_delivery_d = null;
                orderLine.ol_quantity = i_qty;
                orderLine.ol_amount = orderLineAmount;
                orderLine.ol_dist_info = ("s_dist_" + stockDistrictId);
                
                LedgerUtils.createOrderLine(ctx, orderLine.toString());

                // 2.4.3.3 The emulated terminal must display, in the appropriate fields of
                // the input/ output screen, all input data and the output data resulting
                // from the execution of the transaction. The display field s are divided in
                // two groups as follows:
                // - One repeating group of fields: OL_SUPPLY_W_ID, OL_I_ID, I_NAME,
                // OL_QUANTITY, S_QUANTITY, brand_generic, I_PRICE, and OL_AMOUNT. The group
                // is repeated O_OL_CNT times (once per item in the order), equal to the
                // computed value of ol_cnt.
                
                itemsData.add(new ItemsData(orderLine.ol_supply_w_id, orderLine.ol_i_id, item.i_name, orderLine.ol_quantity, stock.s_quantity, 
                brandGeneric, item.i_price, orderLine.ol_amount));
                // itemsData.add("ol_supply_w_id", orderLine.ol_supply_w_id);
                // itemsData.add("ol_i_id", orderLine.ol_i_id);
                // itemsData.add("i_name", item.i_name);
                // itemsData.add("ol_quantity", orderLine.ol_quantity);
                // itemsData.add("s_quantity", stock.s_quantity);
                // itemsData.add("brand_generic", brandGeneric);
                // itemsData.add("i_price", item.i_price);
                // itemsData.add("ol_amount", orderLine.ol_amount);
            }



            // The total-amount for the complete order is computed as:
            // sum(OL_AMOUNT) * (1 - C_DISCOUNT) * (1 + W_TAX + D_TAX)
            Double  totalAmount = totalOrderLineAmount * (1 - customer.c_discount) * (1 + warehouse.w_tax + district.d_tax);

            // 2.4.3.3 The emulated terminal must display, in the appropriate fields of
            // the input/ output screen, all input data and the output data resulting
            // from the execution of the transaction. The display field s are divided in
            // two groups as follows:
            // - One non-repeating group of fields: W_ID, D_ID, C_ID, O_ID, O_OL_CNT,
            // C_LAST, C_CREDIT, C_DISCOUNT, W_TAX, D_TAX, O_ENTRY_D, total_amount, and an
            // optional execution status message other than "Item number is not valid".

            DoNewOrderOutput output = new DoNewOrderOutput();
            output.w_id= warehouse.w_id;
            output.d_id= district.d_id;
            output.c_id= customer.c_id;
            output.c_last= customer.c_last;
            output.c_credit= customer.c_credit;
            output.c_discount = customer.c_discount;
            output.w_tax = warehouse.w_tax;
            output.d_tax= district.d_tax;
            output.o_ol_cnt= order.o_ol_cnt;
            output.o_id= order.o_id;
            output.o_entry_d= order.o_entry_d;
            output.total_amount= totalAmount;
            output.items = itemsData;



            // log(`Finished New Order TX with output: ${JSON.stringify(output)}`, ctx, 'info');
            return output;
        } catch (Exception err) {
            common.log(err.toString(), ctx, "error");
            //throw err;
        }
        return null;
    }

    @Transaction
    /**
     * Performs the Order Status read TX profile.
     * @param ctx The TX context.
     * @param parameters The JSON encoded parameters of the TX profile.
     * @return The JSON encoded query results according to the specification.
     */
    public static DoOrderStatusOutput doOrderStatus(Context ctx, String parameters) {
        // TPC-C 2.6.2.2
        // log(`Starting Order Status TX with parameters: ${parameters}`, ctx, 'info');
        try {
            final OrderStatusParameters params = ParseUtils.parseOrderStatusParameters(parameters);

            Customer customer = LedgerUtils.getCustomersByIdOrLastName(ctx, params.w_id, params.d_id, params.c_id, params.c_last);

            // The row in the ORDER table with matching O_W_ID (equals C_W_ID), O_D_ID (equals C_D_ID),
            // O_C_ID (equals C_ID), and with the largest existing O_ID, is selected. This is the most
            // recent order placed by that customer. O_ID, O_ENTRY_D, and O_CARRIER_ID are retrieved.
            Order order = LedgerUtils.getLastOrderOfCustomer(ctx, customer.c_w_id, customer.c_d_id, customer.c_id);

            // All rows in the ORDER-LINE table with matching OL_W_ID (equals O_W_ID), OL_D_ID (equals
            // O_D_ID), and OL_O_ID (equals O_ID) are selected and the corresponding sets of OL_I_ID,
            // OL_SUPPLY_W_ID, OL_QUANTITY, OL_AMOUNT, and OL_DELIVERY_D are retrieved.
            //ctx.txinfo.md_tpcc_order_status_order_lines = order.o_ol_cnt;

            List<OrderLineData> orderLineData = new ArrayList<>();
            for (int i = 1; i <= order.o_ol_cnt; i++) {
                OrderLine orderLine = LedgerUtils.getOrderLine(ctx, order.o_w_id, order.o_d_id, order.o_id, i);
                
                orderLineData.add(new OrderLineData(orderLine.ol_supply_w_id, orderLine.ol_i_id, 
                orderLine.ol_quantity, orderLine.ol_amount, orderLine.ol_delivery_d));
                // orderLineData.add("ol_supply_w_id", orderLine.ol_supply_w_id);
                // orderLineData.add("ol_i_id", orderLine.ol_i_id);
                // orderLineData.add("ol_quantity", orderLine.ol_quantity);
                // orderLineData.add("ol_amount", orderLine.ol_amount);
                // orderLineData.add("ol_delivery_d", orderLine.ol_delivery_d);                
                
            }

            // 2.6.3.3 The emulated terminal must display, in the appropriate fields of the input/output
            // screen, all input data and the output data resulting from the execution of the
            // transaction. The display fields are divided in two groups as follows:
            // - One non-repeating group of fields: W_ID, D_ID, C_ID, C_FIRST, C_MIDDLE, C_LAST,
            //   C_BALANCE, O_ID, O_ENTRY_D, and O_CARRIER_ID;
            // - One repeating group of fields: OL_SUPPLY_W_ID, OL_I_ID, OL_QUANTITY, OL_AMOUNT, and
            //   OL_DELIVERY_D. The group is repeated O_OL_CNT times (once per item in the order).

            DoOrderStatusOutput output = new DoOrderStatusOutput();
            output.w_id= params.w_id;
            output.d_id= params.d_id;
            output.c_id= customer.c_id;
            output.c_first = customer.c_first;
            output.c_middle = customer.c_middle;
            output.c_last= customer.c_last;
            output.c_balance = customer.c_balance;
            output.o_id= order.o_id;
            output.o_entry_d= order.o_entry_d;
            output.o_carrier_id= order.o_carrier_id;
            output.order_lines= orderLineData;


            // log(`Finished Order Status TX with output: ${JSON.stringify(output)}`, ctx, 'info');
            return output;
        }
        catch (Exception err) {
            common.log(err.toString(), ctx, "error");
            //throw err;
        }
        return null;
    }

    @Transaction
    /**
     * Performs the Payment read-write TX profile.
     * @param ctx The TX context.
     * @param parameters The JSON encoded parameters of the TX profile.
     * @return The JSON encoded query results according to the specification.
     */
    public static DoPaymentOutput doPayment(Context ctx, String parameters) {
        //addTxInfo(ctx);

        // TPC-C 2.5.2.2
        // log(`Starting Payment TX with parameters: ${parameters}`, ctx, 'info');
        try {
            PaymentParameters params = ParseUtils.parsePaymentParameters(parameters);

            // The row in the WAREHOUSE table with matching W_ID is selected. W_NAME,
            // W_STREET_1, W_STREET_2, W_CITY, W_STATE, and W_ZIP are retrieved and W_YTD, the
            // warehouse's year-to-date balance, is increased by H_ AMOUNT.
            Warehouse warehouse = LedgerUtils.getWarehouse(ctx, params.w_id);
            warehouse.w_ytd += params.h_amount;
            LedgerUtils.updateWarehouse(ctx, warehouse);

            // The row in the DISTRICT table with matching D_W_ID and D_ID is selected.
            // D_NAME, D_STREET_1, D_STREET_2, D_CITY, D_STATE, and D_ZIP are retrieved and
            // D_YTD, the district's year-to-date balance, is increased by H_AMOUNT.
            District district = LedgerUtils.getDistrict(ctx, warehouse.w_id, params.d_id);
            district.d_ytd += params.h_amount;
            LedgerUtils.updateDistrict(ctx, district);

            Customer customer = LedgerUtils.getCustomersByIdOrLastName(ctx, warehouse.w_id, district.d_id, params.c_id, params.c_last);

            // C_BALANCE is decreased by H_AMOUNT. C_YTD_PAYMENT is increased by H_AMOUNT.
            // C_PAYMENT_CNT is incremented by 1.
            customer.c_balance -= params.h_amount;
            customer.c_ytd_payment += params.h_amount;
            customer.c_payment_cnt += 1;

            // If the value of C_CREDIT is equal to "BC", then C_DATA is also retrieved from
            // the selected customer and the following history information: C_ID, C_D_ID,
            // C_W_ID, D_ID, W_ID, and H_AMOUNT, are inserted at the left of the C_DATA field
            // by shifting the existing content of C_DATA to the right by an equal number of
            // bytes and by discarding the bytes that are shifted out of the right side of the
            // C_DATA field. The content of the C_DATA field never exceeds 500 characters. The
            // selected customer is updated with the new C_DATA field.
            // if (customer.c_credit == "BC") {
            //     String history = String.join(" ",customer.c_id, customer.c_d_id, customer.c_w_id, district.d_id, warehouse.w_id, params.h_amount);
            //     customer.c_data = history + '|' + customer.c_data;
            //     if (customer.c_data.length > 500)
            //         customer.c_data = customer.c_data.slice(0, 500);
            //}
            if (customer.c_credit.equals("BC")) {
                String history = customer.c_id + " " +  customer.c_d_id + " " +  customer.c_w_id + " " +  district.d_id + " " +  warehouse.w_id + " " +  params.h_amount;
                customer.c_data = history + "|" + customer.c_data;
                if (customer.c_data.length() > 500)
                    customer.c_data = customer.c_data.substring(0, 500);
            }

            LedgerUtils.updateCustomer(ctx, customer);

            // H_DATA is built by concatenating W_NAME and D_NAME separated by 4 spaces.
            //let h_data = warehouse.w_name + ' '.repeat(4) + district.d_name;
            String h_data = warehouse.w_name + "    " + district.d_name;

            // A new row is inserted into the HISTORY table with H_C_ID = C_ID,
            // H_C_D_ID = C_D_ID, H_C_W_ID = C_W_ID, H_D_ID = D_ID, and H_W_ID = W_ID.
            History history = new History();
                history.h_c_id = customer.c_id;
                history.h_c_d_id= customer.c_d_id;
                history.h_c_w_id= customer.c_w_id;
                history.h_d_id= district.d_id;
                history.h_w_id= warehouse.w_id;
                history.h_date= params.h_date;
                history.h_amount= params.h_amount;
                history.h_data= h_data;
            
            LedgerUtils.createHistory(ctx, history.toString());

            // 2.5.3.3 The emulated terminal must display, in the appropriate fields of the
            // input/output screen, all input data and the output data resulting from the
            // execution of the transaction. The following fields are displayed: W_ID, D_ID,
            // C_ID, C_D_ID, C_W_ID, W_STREET_1, W_STREET_2, W_CITY, W_STATE, W_ZIP,
            // D_STREET_1, D_STREET_2, D_CITY, D_STATE, D_ZIP, C_FIRST, C_MIDDLE, C_LAST,
            // C_STREET_1, C_STREET_2, C_CITY, C_STATE, C_ZIP, C_PHONE, C_SINCE, C_CREDIT,
            // C_CREDIT_LIM, C_DISCOUNT, C_BALANCE, the first 200 characters of C_DATA (only
            // if C_CREDIT = "BC"), H_AMOUNT, and H_DATE.
            DoPaymentOutput output = new DoPaymentOutput();
            output.w_id = warehouse.w_id;
            output.d_id = district.d_id;
            output.c_id = customer.c_id;
            output.c_d_id = customer.c_d_id;
            output.c_w_id = customer.c_w_id;
            output.h_amount = history.h_amount;
            output.h_date = history.h_date;
            output.w_street_1 = warehouse.w_street_1;
            output.w_street_2 = warehouse.w_street_2;
            output.w_city = warehouse.w_city;
            output.w_state = warehouse.w_state;
            output.w_zip = warehouse.w_zip;
            output.d_street_1 = district.d_street_1;
            output.d_street_2 = district.d_street_2;
            output.d_city = district.d_city;
            output.d_state = district.d_state;
            output.d_zip = district.d_zip;
            output.c_first= customer.c_first;
            output.c_middle= customer.c_middle;
            output.c_last= customer.c_last;
            output.c_street_1 = customer.c_street_1;
            output.c_street_2 = customer.c_street_2;
            output.c_city = customer.c_city;
            output.c_state = customer.c_state;
            output.c_zip = customer.c_zip;
            output.c_phone = customer.c_phone;
            output.c_since = customer.c_since;
            output.c_credit = customer.c_credit;
            output.c_credit_lim = customer.c_credit_lim;
            output.c_discount = customer.c_discount;
            output.c_balance = customer.c_balance;
            

            if (customer.c_credit.equals("BC")) {
                output.c_data = customer.c_data.substring(0, 200);
            }

            // log(`Finished Payment TX with output: ${JSON.stringify(output)}`, ctx, 'info');
            return output;
        }
        catch (Exception err) {
            common.log(err.toString(), ctx, "error");
            //throw err;
        }
        return null;
    }

    @Transaction
    /**
     * Performs the Stock Level read TX profile.
     * @param ctx The TX context.
     * @param parameters The JSON encoded parameters of the TX profile.
     * @return The JSON encoded query results according to the specification.
     */
    public static DoStockLevelOutput doStockLevel(Context ctx, String parameters) {
        //addTxInfo(ctx);

        // TPC-C 2.8.2.2
        // log(`Starting Stock Level TX with parameters: ${parameters}`, ctx, 'info');
        try {
            final StockLevelParameters params = ParseUtils.parseStockLevelParameters(parameters);

            // The row in the DISTRICT table with matching D_W_ID and D_ID is selected and D_NEXT_O_ID
            // is retrieved.
            final District district = LedgerUtils.getDistrict(ctx, params.w_id, params.d_id);

            // All rows in the ORDER-LINE table with matching OL_W_ID (equals W_ID), OL_D_ID (equals
            // D_ID), and OL_O_ID (lower than D_NEXT_O_ID and greater than or equal to D_NEXT_O_ID
            // minus 20) are selected. They are the items for 20 recent orders of the district.

            final int o_id_min = district.d_next_o_id - 20;
            final int o_id_max = district.d_next_o_id;
            List<Integer> recentItemIds = LedgerUtils.getItemIdsOfRecentOrders(ctx, params.w_id, district.d_id, o_id_min, o_id_max);
            //ctx.txinfo.md_tpcc_stock_level_recent_items = recentItemIds.length;

            // All rows in the STOCK table with matching S_I_ID (equals OL_I_ID) and S_W_ID (equals W_ID)
            // from the list of distinct item numbers and with S_QUANTITY lower than threshold are counted
            // (giving low_stock).
            int lowStock = 0;
            for (int i_id : recentItemIds) {
                Stock stock = LedgerUtils.getStock(ctx, params.w_id, i_id);
                if (stock.s_quantity < params.threshold) {
                    lowStock += 1;
                }
            }

            // 2.8.3.3 The emulated terminal must display, in the appropriate field of the input/
            // output screen, all input data and the output data which results from the execution of
            // the transaction. The following fields are displayed: W_ID, D_ID, threshold, and low_stock.

            DoStockLevelOutput output = new DoStockLevelOutput();
            output.w_id= params.w_id;
            output.d_id= params.d_id;
            output.threshold= params.threshold;
            output.low_stock= lowStock;


            // log(`Finished Stock Level TX with output: ${JSON.stringify(output)}`, ctx, 'info');
            return output;

        }
        catch (Exception err) {
            common.log(err.toString(), ctx, "error");
            //throw err;
        }
        return null;
    }

    @Transaction
    /**
     * Initializes the TPC-C chaincode.
     * @param ctx The TX context.
     */
    public static void instantiate(Context ctx) {
        common.log("Instantiating TPC-C chaincode", ctx, "info");
    }
}










