/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import org.example.common.TABLES;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyValue;
//import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Utility functions for accessing the state database.
 */

 @DataType()
public class LedgerUtils {
    private static final Logger LOGGER = Logger.getLogger(TPCC.class.getName());
    static Gson gson = new Gson();
    

    /**
     * LOW-LEVEL API
     */

    /**
     * Adds a new record to the state database. Throws an error if it already exists.
     * @param ctx The TX context.
     * @param type The type of the record.
     * @param keyParts The parts of the record's primary key.
     * @param entry The JSON string/object representation of the record.
     * @throws Exception
     */
    public static void createEntry(Context ctx, String type, String[] keyParts, Object entry) throws Exception {
        LOGGER.info("Begin createEntry for input " + type + " and " + keyParts);
        CompositeKey key = ctx.getStub().createCompositeKey(type, keyParts);
        LOGGER.info("Created composite key " + key.toString());

        String entryString = entry instanceof String ? (String) entry : gson.toJson(entry);
               
        byte[] buffer = entryString.getBytes(StandardCharsets.UTF_8);

        LOGGER.info("Entry string " + entryString + " to bytes " + buffer);

        //final long start = System.currentTimeMillis();
        ctx.getStub().putState(key.toString(), buffer);

        LOGGER.info("createEntry() Created entry for key " + key + " With value: " + buffer);
        
    }

    /**
     * Adds a new record to the state database. Throws an error if it already exists.
     * @param ctx The TX context.
     * @param type The type of the record.
     * @param keyParts The parts of the record's primary key.
     * @param entry The JSON string/object representation of the record.
     * @throws Exception
     */
    public static void updateEntry(Context ctx, String type, String[] keyParts, Object entry) throws Exception {
        LOGGER.info("Beginning updateEntry for type " + type + " " + keyParts);
        CompositeKey key = ctx.getStub().createCompositeKey(type, keyParts);
        String entryString = entry instanceof String ? (String) entry : gson.toJson(entry);
        
        byte[] buffer = entryString.getBytes(StandardCharsets.UTF_8);
        
        ctx.getStub().putState(key.toString(), buffer);   

        LOGGER.info("UpdateEntry complete for Key " +key + "Value: " + buffer);     
    }

    /**
     * Retrieves the given record from the state database.
     * @param ctx The TX context.
     * @param type The type of the record.
     * @param keyParts The attribute values as key parts.
     * @return The retrieved data or null if not found.
     */
    public static String getEntry(Context ctx, String type, String[] keyParts) throws Exception {
        CompositeKey key = ctx.getStub().createCompositeKey(type, keyParts);
        LOGGER.info("LedgerUtils.getEntry for " + key.toString());

        //final long start = System.currentTimeMillis();
        byte[] data = ctx.getStub().getState(key.toString());
        //final long end = System.currentTimeMillis();

        // ctx.getTxinfo().stat_get_cnt += 1;
        // ctx.getTxinfo().stat_get_exec_total_ms += end - start;
        LOGGER.info("getEntry bytes retieved is " + data);
        if (data.length > 0) {
            //ctx.getTxinfo().stat_get_size_bytes += data.length;

            String entry = new String(data, StandardCharsets.UTF_8);           
            
            LOGGER.info("LedgerUtils.getEntry Retrieved entry " + entry);
            return entry;
        }
        LOGGER.info("getEntry for: " + key+ " FAILED");
        return null;
    }

    /**
     * Deletes the given record from the state database.
     * @param ctx The TX context.
     * @param type The type of the record.
     * @param keyParts The attribute values as key parts.
     * @throws Exeption
     */
    public static void deleteEntry(Context ctx, String type, String[] keyParts) throws Exception {
        CompositeKey key = ctx.getStub().createCompositeKey(type, keyParts);
        
        //final long start = System.currentTimeMillis();
        ctx.getStub().delState(key.toString());
        LOGGER.info("Deleted state for entry: " + key.toString());
        //final long end = System.currentTimeMillis();

        // ctx.getTxinfo().stat_del_cnt += 1;
        // ctx.getTxinfo().stat_del_exec_total_ms += end - start;
        // log("Deleted entry: " + key, ctx);
    }  
    
    /**
     * Retrieves entries from the state database that matches certain criteria.
     * @param ctx The TX context.
     * @param type The type of entries to search.
     * @param keyParts The parts of the entries private key.
     * @param matchFunction The function that determines matches.
     * @param firstMatch Indicates whether only the first match should be returned.
     * @return The retrieved entries.
     * @throws Exception
     */
    public static List<Object> select(Context ctx, String type, String[] keyParts, Function<String, Object> matchFunction, boolean firstMatch) throws Exception {
        LOGGER.info("Start of select function:");
        CompositeKey compositeKey = ctx.getStub().createCompositeKey(type, keyParts);
        Iterator<KeyValue> iterator = ctx.getStub().getStateByPartialCompositeKey(compositeKey.toString()).iterator();
        //QueryResultsIterator<KeyValue> iterator = ctx.getStub().getStateByPartialCompositeKey(compositeKey.toString());
   
        List<Object> matches = new ArrayList<>();
        //int retrieved = 0;
        try {
            LOGGER.info("iteration to retrieve next entry");
            while (iterator.hasNext()) {                
                KeyValue res = iterator.next(); 
                //retrieved += 1;
                byte[] buffer = res.getValue();  
                String entry = new String(buffer, StandardCharsets.UTF_8);
                LOGGER.info("Enumerated entry:");

                Object match = matchFunction.apply(entry);
                if (match != null) {
                    LOGGER.info("Add matches to list");
                    matches.add(match);
                    if (firstMatch) {
                        break;
                    }
                }
            }
            LOGGER.info("Exit SELECT()");
        } catch (Exception e) {
            common.log(e.toString(), ctx, "error");
            throw e;        
        } 
        return firstMatch ? matches.subList(0, 1) : matches;
    }
    /**
     * WAREHOUSE API
     */

    /**
     * Adds a new warehouse to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @throws Exception
     */
    public static void createWarehouse(Context ctx, Object entry) throws Exception {
        LOGGER.info("Starting create warehouse with entry " + entry);

        Warehouse warehouse = entry instanceof String ? ParseUtils.parseWarehouse((String) entry) : (Warehouse) entry;
        
        LOGGER.info("Call to createEntry() for warehouse with ID " + warehouse.w_id);
        LedgerUtils.createEntry(ctx, TABLES.WAREHOUSE, new String[]{common.pad(warehouse.w_id)}, entry);
        LOGGER.info("createEntry() for warehouse " + warehouse.w_id + "COMPLETE!!!!");
    }

    /**
     * Retrieves a warehouse record from the ledger.
     * @param ctx The TX context.
     * @param w_id The warehouse ID.
     * @return The retrieved warehouse.
     */
    public static Warehouse getWarehouse(Context ctx, int w_id) throws Exception{
        LOGGER.info("LedgerUtils.getWarehouse: Attempt to retrieve warehouse details  for warehouse: " + w_id);
        String entry = LedgerUtils.getEntry(ctx, TABLES.WAREHOUSE, new String[]{common.pad(w_id)});

        if (entry == null) {
            throw new Exception("Could not retrieve Warehouse(" + w_id + ")");
        }
        LOGGER.info("LedgerUtils.getWarehouse: retrieved warehouse details " + entry);
        return entry != null ? ParseUtils.parseWarehouse(entry) : null;
    }

    /**
     * Updates a warehouse in the state database.
     * @param ctx The TX context.
     * @param entry The warehouse object.
     * @throws Exception
     */
    public static void updateWarehouse(Context ctx, Warehouse entry) throws Exception {   
        LOGGER.info("Begin update Warehouse ");
        LedgerUtils.updateEntry(ctx, TABLES.WAREHOUSE, new String[]{common.pad(entry.w_id)}, entry);
        LOGGER.info("updateWarehouse COMPLETE!!!");
    }

    /**
     * DISTRICT API
     */

    /**
     * Adds a new district to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string or District object.
     * @throws Exception
     */
    public static void createDistrict(Context ctx, Object entry) throws Exception {
        LOGGER.info("Begin create entry for District with entry " + entry);
        District district = entry instanceof String ? ParseUtils.parseDistrict((String) entry) : (District) entry;
        LedgerUtils.createEntry(ctx, TABLES.DISTRICT, new String[]{common.pad(district.d_w_id), common.pad(district.d_id)}, entry);
        LOGGER.info("CreateDistrict SUCCESS!!!");
    }

    /**
     * Retrieves a district record from the ledger.
     * @param ctx The TX context.
     * @param d_w_id The warehouse ID.
     * @param d_id The district ID.
     * @return The retrieved district.
     */
    public static District getDistrict(Context ctx, int d_w_id, int d_id) throws Exception{
        String[] keyParts = new String[]{common.pad(d_w_id), common.pad(d_id)};
        LOGGER.info("Begin getDistrict with key " + keyParts);

        String entry = LedgerUtils.getEntry(ctx, TABLES.DISTRICT, keyParts);
        if (entry == null) {
            throw new Exception("Could not retrieve District(" + d_w_id + ", " + d_id + ")");
        }
        LOGGER.info("getDistrict for " + keyParts + "returned with entry " + entry);
        return entry != null ? ParseUtils.parseDistrict(entry) : null;        
    }

    /**
     * Updates a district in the state database.
     * @param ctx The TX context.
     * @param entry The district object.
     * @throws Exeption
     */
    public static void updateDistrict(Context ctx, District entry) throws Exception {
        String[] keyParts = new String[]{common.pad(entry.d_w_id), common.pad(entry.d_id)};
        LOGGER.info("updateDistrict entry: " + keyParts);
        LedgerUtils.updateEntry(ctx, TABLES.DISTRICT, keyParts, entry);
        LOGGER.info("Distruct entry updated");
    }

    /**
     * CUSTOMER API
     */

    /**
     * Adds a new customer to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @throws Exception
     */
    public static void createCustomer(Context ctx, Object entry) throws Exception {
        LOGGER.info("Starting createCustomer");
        Customer customer = entry instanceof String ? ParseUtils.parseCustomer((String) entry) : (Customer) entry;

        String[] keyParts = new String[]{common.pad(customer.c_w_id), common.pad(customer.c_d_id), common.pad(customer.c_id)};
        LOGGER.info("Begin createCustomer entry with keypart: " + keyParts);
        LedgerUtils.createEntry(ctx, TABLES.CUSTOMER, keyParts, entry);
        
        String[] lastNameKeyParts = new String[]{common.pad(customer.c_w_id), common.pad(customer.c_d_id), customer.c_last, common.pad(customer.c_id)};
        LOGGER.info("createCustomer entry with last name : " + lastNameKeyParts);
        LedgerUtils.createEntry(ctx, TABLES.CUSTOMER_LAST_NAME, lastNameKeyParts, entry);

        LOGGER.info("Create customer Complete for " + keyParts + " last name " + lastNameKeyParts);
    }

    /**
     * Retrieves a customer record from the ledger.
     * @param ctx The TX context.
     * @param c_w_id The warehouse ID.
     * @param c_d_id The district ID.
     * @param c_id The customer ID.
     * @return The retrieved customer.
     */
    public static Customer getCustomer(Context ctx, int c_w_id, int c_d_id, int c_id) throws Exception {
        String[] keyParts = new String[]{common.pad(c_w_id), common.pad(c_d_id), common.pad(c_id)};
        LOGGER.info("getCustomer with key " + keyParts);
        String entry = LedgerUtils.getEntry(ctx, TABLES.CUSTOMER, keyParts);
        if (entry == null) {
            throw new Exception("Could not retrieve Customer(" + c_w_id + ", " + c_d_id + " , " + c_id +")");
        }
        LOGGER.info("Customer entry" + entry + "retrieved");
        return entry != null ? ParseUtils.parseCustomer(entry) : null;
    }

    /**
     * Retrieves the customers from the state database that match the given partial key
     * @param ctx The TX context.
     * @param c_w_id The warehouse ID of the customer.
     * @param c_d_id The district ID of the customer.
     * @param c_last The last name of the customer.
     * @return The retrieved customers.
     * @throws Exception
     */
    public static List<Customer> getCustomersByLastName(Context ctx, int c_w_id, int c_d_id, String c_last) throws Exception {
        LOGGER.info("getCustomerByLastName");
        Function<String, Object> matchFunction = entry -> {
            Customer customer = ParseUtils.parseCustomer(entry);
            if(customer.c_last == c_last)
            {
                LOGGER.info("Customer's last name " + customer.c_last + "== " + "Last Name" + c_last );
                return customer;
            }
            LOGGER.info("Customer's last name " + customer.c_last + "!= to" + "Last Name" + c_last );
            return null;
        };
        String[] keyParts = new String[]{common.pad(c_w_id), common.pad(c_d_id), c_last};  
        List<Object> entries = select(ctx, TABLES.CUSTOMER_LAST_NAME, keyParts, matchFunction, false);

        if (entries.size() == 0) {
            throw new Exception(String.format("Could not find Customers(%d, %d, c_id) matching last name \"%s\"", c_w_id, c_d_id, c_last));
        }

        List<Customer> customers = new ArrayList<>();
        for (Object entry : entries) {
            customers.add((Customer) entry);
            LOGGER.info("List of Customers");
        }
        
        return customers;
    }

    /**
     * Retrieves the customers from the state database that match the given ID or last name
     * @param ctx The TX context.
     * @param c_w_id The warehouse ID of the customer.
     * @param c_d_id The district ID of the customer.
     * @param c_id The customer ID.
     * @param c_last The last name of the customer.
     * @return The retrieved customers.
     * @throws Exception if neither the customer ID nor the customer last name parameter is supplied
     */
    public static Customer getCustomersByIdOrLastName(Context ctx, int c_w_id, int c_d_id, Integer c_id, String c_last) throws Exception {
        if (c_id != null) {
            // Case 1, the customer is selected based on customer number: the row in the CUSTOMER
            // table with matching C_W_ID, C_D_ID, and C_ID is selected and C_BALANCE, C_FIRST,
            // C_MIDDLE, and C_LAST are retrieved.
            return LedgerUtils.getCustomer(ctx, c_w_id, c_d_id, c_id);
        } else if (c_last != null) {
            // Case 2, the customer is selected based on customer last name: all rows in the
            // CUSTOMER table with matching C_W_ID, C_D_ID and C_LAST are selected sorted by
            // C_FIRST in ascending order. Let n be the number of rows selected. C_BALANCE,
            // C_FIRST, C_MIDDLE, and C_LAST are retrieved from the row at position n/ 2 rounded up
            // in the sorted set of selected rows from the CUSTOMER table.
            List<Customer> customerList = LedgerUtils.getCustomersByLastName(ctx, c_w_id, c_d_id, c_last);
            customerList.sort((c1, c2) -> c1.c_first.compareTo(c2.c_first));
            int position = (int) Math.ceil(customerList.size() / 2.0);
            return customerList.get(position - 1);
        } 
        else {
            throw new Exception("Neither the customer ID nor the customer last name parameter is supplied");
        }    
    }

    /**
     * Updates a customer in the state database.
     * @param ctx The TX context.
     * @param entry The customer object.
     * @throws Exception
     */
    public static void updateCustomer(Context ctx, Customer entry) throws Exception {

        String[] keyParts = new String[]{common.pad(entry.c_w_id), common.pad(entry.c_d_id), common.pad(entry.c_id)};
        LOGGER.info("update customer entry" + keyParts);
        LedgerUtils.updateEntry(ctx, TABLES.CUSTOMER, keyParts, entry);

        String[] lastNameKeyParts = new String[]{common.pad(entry.c_w_id), common.pad(entry.c_d_id), entry.c_last, common.pad(entry.c_id)};
        LOGGER.info("update customer Last name entry table with" + lastNameKeyParts);
        LedgerUtils.updateEntry(ctx, TABLES.CUSTOMER_LAST_NAME, lastNameKeyParts, entry);
    } 
    
    /**
     * HISTORY API
     */

    /**
     * Adds a new history to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @throws Exception
     */
    public static void createHistory(Context ctx, Object entry) throws Exception {
        History history = entry instanceof String ? ParseUtils.parseHistory((String) entry) : (History) entry;

        String[] keyParts = new String[]{common.pad(history.h_c_w_id), common.pad(history.h_c_d_id), common.pad(history.h_c_id), history.h_date};
        LedgerUtils.createEntry(ctx, TABLES.HISTORY, keyParts, entry);
    }

    /**
     * NEW ORDER API
     */

    /**
     * Adds a new new order to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @
     */
    public static void createNewOrder(Context ctx, Object entry) throws Exception { 
        LOGGER.info("Create new order entry");
        NewOrder newOrder = entry instanceof String ? ParseUtils.parseNewOrder((String) entry) : (NewOrder) entry;

        String[] keyParts = new String[]{common.pad(newOrder.no_w_id), common.pad(newOrder.no_d_id), common.pad(newOrder.no_o_id)};
        LedgerUtils.createEntry(ctx, TABLES.NEW_ORDER, keyParts, entry);
        LOGGER.info("Create new order entry COMPLETE!!");
    }
  
    /**
     * Retrieves the oldest new order from the state database that matches the given partial key.
     * @param ctx The TX context.
     * @param no_w_id The new order's warehouse ID.
     * @param no_d_id The new order's district ID.
     * @return The oldest new order.
     */
    static NewOrder getOldestNewOrder(Context ctx, int no_w_id, int no_d_id) throws Exception {
        LOGGER.info("Searching for oldest New Order for warehouse " + no_w_id +" and district " + no_d_id );
        List<Object> oldest = LedgerUtils.select(ctx, TABLES.NEW_ORDER, new String[]{common.pad(no_w_id), common.pad( no_d_id)}, ParseUtils::parseNewOrder, true);
        // if (oldest != null) {
        //     LOGGER.info("Retrieved oldest oldest New Order( " + no_w_id + "," + no_d_id + "," + oldest.no_o_id + ")");
        // }
        LOGGER.info("Retrieved oldest oldest New Order( " + oldest);
        return (NewOrder) oldest;
    }

    /**
     * Deletes a new order from the state database.
     * @param ctx The TX context.
     * @param entry The new order object.
     * @
     */
    public static void deleteNewOrder(Context ctx, NewOrder entry) throws Exception {
        LOGGER.info("Begin delete oldest NewOrder" + gson.toJson(entry));
        String[] keyParts = new String[]{common.pad(entry.no_w_id), common.pad(entry.no_d_id), common.pad(entry.no_o_id)};
        LOGGER.info("delete oldest NewOrder entry with key " + keyParts);
        LedgerUtils.deleteEntry(ctx, TABLES.NEW_ORDER, keyParts);
        LOGGER.info("Oldest NewOrder entry with key " + keyParts + "deleted");

    }

    /**
     * ORDER API
     */

    /**
     * Adds a new order to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @
     */
    public static void createOrder(Context ctx, Object entry) throws Exception {
        Order order = entry instanceof String ? ParseUtils.parseOrder((String) entry) : (Order) entry;

        String[] keyParts = new String[] {common.pad(order.o_w_id), common.pad(order.o_d_id), common.pad(Integer.MAX_VALUE - order.o_id)};
        LedgerUtils.createEntry(ctx, TABLES.ORDERS, keyParts, entry);
    }

    /**
     * Retrieves an order record from the ledger.
     * @param ctx The TX context.
     * @param o_w_id The order's warehouse ID.
     * @param o_d_id The order's district ID.
     * @param o_id The order ID.
     * @return  The retrieved order.
     */
    public static Order getOrder(Context ctx, int o_w_id, int o_d_id, int o_id) throws Exception {
        String[] keyParts = new String[]{common.pad(o_w_id), common.pad(o_d_id), common.pad(Integer.MAX_VALUE - o_id)};
        LOGGER.info("Begin getOrder with entry: " + keyParts);
        String entry = LedgerUtils.getEntry(ctx, TABLES.ORDERS, keyParts);
        
        if (entry == null) {
            throw new Exception("Could not retrieve Order(" + o_w_id + ", " + o_d_id + ", "+ o_id + ")");
        }
        LOGGER.info("getOrder returned " + entry);
        return entry != null ? ParseUtils.parseOrder(entry) : null;
    }

    /**
     * Retrieves the last of a customer from the state database.
     * @param ctx The TX context.
     * @param o_w_id The warehouse ID of the order.
     * @param o_d_id The district ID of the order.
     * @param o_c_id The customer ID for the order.
     * @return The retrieved order.
     * @throws Exception if the last order is not found.
     */
    public static Order getLastOrderOfCustomer(Context ctx, int o_w_id, int o_d_id, int o_c_id) throws Exception {
        Function<String , Object> matchFunction = entry -> {
        Order order = ParseUtils.parseOrder(entry);
        if (order.o_c_id == o_c_id) {
            return order;
        }
        return null;
        };
        // log(`Searching for last Order(${o_w_id}, ${o_d_id}, o_id) of Customer(${o_w_id}, ${o_d_id}, ${o_c_id})`, ctx);
        
        String[] keyParts = new String[]{common.pad(o_w_id), common.pad(o_d_id)};
        Order lastOrder = (Order) select(ctx, TABLES.ORDERS, keyParts, matchFunction, true);
        if (lastOrder == null) {
            throw new Exception(String.format("Could not find last Order(%d, %d, o_id) of Customer(%d, %d, %d)", o_w_id, o_d_id, o_w_id, o_d_id, o_c_id));
        }
        // log(`Retrieved last Order(${o_w_id}, ${o_d_id}, ${lastOrder.o_id}) of Customer(${o_w_id}, ${o_d_id}, ${o_c_id})`, ctx);
        return lastOrder;
    }


    /**
     * Updates an order in the state database.
     * @param ctx The TX context.
     * @param entry The order object.
     * @throws Exception
     */
    public static void updateOrder(Context ctx, Order entry) throws Exception {
        LOGGER.info("Begin update Order");
        String[] keyParts = new String[] {common.pad(entry.o_w_id), common.pad(entry.o_d_id), common.pad(Integer.MAX_VALUE - entry.o_id)};
        LedgerUtils.updateEntry(ctx, TABLES.ORDERS, keyParts, entry);
        LOGGER.info("Order updated");
    }

    /**
     * ORDER LINE API
     */

    /**
     * Adds a new order line to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @
     */
    public static void createOrderLine(Context ctx, Object entry) throws Exception {
        OrderLine orderLine = entry instanceof String ? ParseUtils.parseOrderLine((String) entry) : (OrderLine) entry;

        String[] keyParts = new String[] {common.pad(orderLine.ol_w_id), common.pad(orderLine.ol_d_id), 
            common.pad(orderLine.ol_o_id), common.pad(orderLine.ol_number)};
        LedgerUtils.createEntry(ctx, TABLES.ORDER_LINE, keyParts, entry);
    }

        /**
     * Retrieves an order line record from the ledger.
     * @param ctx The TX context.
     * @param ol_w_id The order's warehouse ID.
     * @param ol_d_id The order's district ID.
     * @param ol_o_id The order ID.
     * @param ol_number The number of the order line.
     * @return {<OrderLine>} The retrieved order line.
     */
    public static OrderLine getOrderLine(Context ctx, int ol_w_id, int ol_d_id, int ol_o_id, int ol_number) throws Exception {
        String[] keyParts = new String[] {common.pad(ol_w_id), common.pad(ol_d_id), common.pad(ol_o_id), common.pad(ol_number)};
        LOGGER.info("Begin getOrdeline entry for keyparts " + keyParts);
        String entry = LedgerUtils.getEntry(ctx, TABLES.ORDER_LINE, keyParts);

        if (entry == null) {
            throw new Exception("Could not retrieve Order Line(" + ol_w_id + "," + ol_d_id + "," + ol_o_id + ", " + ol_number + ")");
        }
        LOGGER.info("getOrdeline entry for keyparts " + keyParts + "Returned " + entry);
        //return entry ? ParseUtils.parseOrderLine(entry) : (OrderLine) null;
        return entry != null ? ParseUtils.parseOrderLine(entry):null;
    }


    /**
     * Updates an order line in the state database.
     * @param ctx The TX context.
     * @param entry The order line object.
     * @
     */
    public static void updateOrderLine(Context ctx, OrderLine entry) throws Exception {

        String[] keyParts = new String[] {common.pad(entry.ol_w_id), common.pad(entry.ol_d_id), 
            common.pad(entry.ol_o_id), common.pad(entry.ol_number)};

        LedgerUtils.updateEntry(ctx, TABLES.ORDER_LINE, keyParts, entry);
    }


    /**
     * ITEM API
     */

    /**
     * Adds a new item to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @throws Exception
     */
    public static void createItem(Context ctx, Object entry) throws Exception {
        Item item = entry instanceof String ? ParseUtils.parseItem((String) entry) : (Item) entry;
        LedgerUtils.createEntry(ctx, TABLES.ITEM, new String[]{common.pad(item.i_id)}, entry);
    }

    /**
     * Retrieves an item record from the ledger.
     * @param ctx The TX context.
     * @param i_id The item ID.
     * @return {<Item>} The retrieved item.
     */
    public static Item getItem(Context ctx, int i_id) throws Exception {
        String entry = LedgerUtils.getEntry(ctx, TABLES.ITEM, new String[] {common.pad(i_id)});
        //return entry ? ParseUtils.parseItem(entry) : (Item) null;
        return entry != null ? ParseUtils.parseItem(entry): null;
    }

    /**
     * Counts the number of items whose stock is below a given threshold.
     * @param ctx The TX context.
     * @param w_id The warehouse ID.
     * @param d_id The district ID.
     * @param o_id_min The oldest/minimum order ID to consider (inclusive).
     * @param o_id_max The newest/maximum order ID to consider (exclusive).
     * @return The unique IDs of items from the recent orders.
     */
    public static List<Integer> getItemIdsOfRecentOrders(Context ctx, int w_id, int d_id, int o_id_min, int o_id_max) throws Exception {
        Set<Integer> itemIds = new HashSet<>();
        
        // log("Retrieving item IDs for Orders(" + w_id + "," + d_id + ", " + o_id_min + ", " + o_id_max + ")"), ctx);
        for (int current_o_id = o_id_min; current_o_id < o_id_max; current_o_id++) {
            Order order = LedgerUtils.getOrder(ctx, w_id, d_id, current_o_id);

            for (int ol_number = 1; ol_number <= order.o_ol_cnt; ol_number++) {
                OrderLine orderLine = LedgerUtils.getOrderLine(ctx, w_id, d_id, current_o_id, ol_number);
                itemIds.add(orderLine.ol_i_id);
            }
        }

        if (itemIds.size() == 0) {
            throw new Exception(String.format("Could not find item IDs of recent Orders(%d, %d, [%d, %d))", w_id, d_id, o_id_min, o_id_max));
        }
        
        //log("Retrieved" + itemIds.size + "item IDs for Orders(" + w_id + "," + d_id + "," +o_id_min + ", " + o_id_max + ")" , ctx);
        
        return new ArrayList<>(itemIds);
    }

    /**
     * STOCK API
     */

    /**
     * Adds a new stock to the state database.
     * @param ctx The TX context.
     * @param entry The JSON string.
     * @async
     */
    public static void createStock(Context ctx, Object entry) throws Exception {
        Stock stock = entry instanceof String ? ParseUtils.parseStock((String) entry) : (Stock) entry;        
        LedgerUtils.createEntry(ctx, TABLES.STOCK, new String[]{common.pad(stock.s_w_id), common.pad(stock.s_i_id)}, entry);
    }

    /**
     * Retrieves a stock record from the ledger.
     * @param ctx The TX context.
     * @param s_w_id The warehouse ID.
     * @param s_i_id The item ID.
     * @return {<Stock>} The retrieved stock.
     */
    public static Stock getStock(Context ctx, int s_w_id, int s_i_id) throws Exception {
        String entry = LedgerUtils.getEntry(ctx, TABLES.STOCK, new String[] {common.pad(s_w_id), common.pad(s_i_id)});
        if (entry == null) {
            throw new Exception("Could not retrieve Stock(" + s_w_id + ", " + s_i_id + ")" );
        }

        //return entry ? ParseUtils.parseStock(entry) : (Stock)null;
        return entry != null ? ParseUtils.parseStock(entry):null;
    }

    /**
     * Updates a stock in the state database.
     * @param ctx The TX context.
     * @param entry The stock object.
     * @async
     */
    public static void updateStock(Context ctx, Stock entry) throws Exception {
        LedgerUtils.updateEntry(ctx, TABLES.STOCK, new String[] {common.pad(entry.s_w_id), common.pad(entry.s_i_id)}, entry);
    }
}
