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

import java.util.logging.Logger;

import org.hyperledger.fabric.contract.annotation.DataType;
import com.google.gson.Gson;
//import com.fasterxml.jackson.databind.ObjectMapper;


@DataType
public class ParseUtils {
    static Gson gson = new Gson();  
    private static final Logger LOGGER = Logger.getLogger(TPCC.class.getName());  
    //private ObjectMapper objectMapper = new ObjectMapper();

   
    /**
     * Parses the parameters of a New Order TX. (To enable IDE code completion)
     * @param params The JSON string of the parameters.
     * @return The parsed parameters.
     */
    public static NewOrderParameters parseNewOrderParameters(String params) throws Exception {
        //return objectMapper.readValue(params, NewOrderParameters.class);
        //return gson.fromJson(params, NewOrderParameters.class);
        NewOrderParameters newOrderParams = gson.fromJson(params, NewOrderParameters.class);
        return newOrderParams;
    }
    

    /**
     * Parses the parameters of a Payment TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {PaymentParameters} The parsed parameters.
     */
    public static PaymentParameters parsePaymentParameters(String params) throws Exception {
        //return objectMapper.readValue(params, PaymentParameters.class);
        PaymentParameters paymentParams = gson.fromJson(params, PaymentParameters.class);
        return paymentParams;
    }

    /**
     * Parses the parameters of a Delivery TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {DeliveryParameters} The parsed parameters.
     */
    public static DeliveryParameters parseDeliveryParameters(String params) throws Exception {
        //return objectMapper.readValue(params, DeliveryParameters.class); 
        LOGGER.info("Parse Delivery parameters " + params);       
        DeliveryParameters deliveryParams = gson.fromJson(params, DeliveryParameters.class);
        LOGGER.info("Delivery parameters are: " +deliveryParams);
        return deliveryParams;
    }

    /**
     * Parses the parameters of an Order Status TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {OrderStatusParameters} The parsed parameters.
     */
    public static OrderStatusParameters parseOrderStatusParameters(String params) throws Exception {
        //return objectMapper.readValue(params, OrderStatusParameters.class);
        OrderStatusParameters oStatusParams = gson.fromJson(params, OrderStatusParameters.class);
        return oStatusParams;
    }

    /**
     * Parses the parameters of an Order Status TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {StockLevelParameters} The parsed parameters.
     */
    public static StockLevelParameters parseStockLevelParameters(String params) throws Exception {
        //return objectMapper.readValue(params, StockLevelParameters.class);
        StockLevelParameters stockLevelParams = gson.fromJson(params, StockLevelParameters.class);
        return stockLevelParams;
    }

    /**
     * Parses the Warehouse entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the warehouse.
     * @return {Warehouse} The warehouse object.
     */
    public static Warehouse parseWarehouse(String jsonString) {
        LOGGER.info("Starting parseWarehouse for " + jsonString + "Warehouse parameters");        
        Warehouse warehouseParams = gson.fromJson(jsonString, Warehouse.class);
        LOGGER.info("parseWarehouse returned " + warehouseParams.toString() + "Warehouse parameters");
        return warehouseParams;
    }

        /**
     * Parses the District entry JSON string. (To enable IDE code completion)
     * @param jsonString The JSON string of the district.
     * @return The district object.
     */
    public static District parseDistrict(String jsonString) {
        District distParams = gson.fromJson(jsonString, District.class);
        return distParams;
    }

    /**
     * Parses the Customer entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the customer.
     * @return {Customer} The customer object.
     */
    public static Customer parseCustomer(String jsonString) {
        LOGGER.info("parse customer parameters");
        Customer custParams = gson.fromJson(jsonString, Customer.class);
        LOGGER.info("return parsed customer parameters");
        return custParams;
    }

    /**
     * Parses the History entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the history.
     * @return {History} The history object.
     */
    public static History parseHistory(String jsonString) {
        History historyParams= gson.fromJson(jsonString, History.class);
        return historyParams;
    }

    /**
     * Parses the New Order entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the new order.
     * @return {NewOrder} The new order object.
     */
    public static NewOrder parseNewOrder(String jsonString) {
        NewOrder newOParams = gson.fromJson(jsonString, NewOrder.class);
        return newOParams;
    }

    /**
     * Parses the Order entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the order.
     * @return {Order} The order object.
     */     
    public static Order parseOrder(String jsonString) {  
        LOGGER.info("parse order parameters: " + jsonString);      
        Order orderParams = gson.fromJson(jsonString, Order.class);
        LOGGER.info("Returned parsed order parameters are: " + gson.toJson(orderParams));
        return orderParams;
    }

    /**
     * Parses the Order Line entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the order line.
     * @return {OrderLine} The order line object.
     */
    public static OrderLine parseOrderLine(String jsonString) {
        OrderLine oLineParams = gson.fromJson(jsonString, OrderLine.class);
        return oLineParams;
    }

    /**
     * Parses the Item entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the item.
     * @return {Item} The item object.
     */
    public static Item parseItem(String jsonString) {
        Item itemParams = gson.fromJson(jsonString, Item.class);
        return itemParams;
    }

    /**
     * Parses the Stock entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the stock.
     * @return {Stock} The stock object.
     */
    public static Stock parseStock(String jsonString) {
        Stock stockParams = gson.fromJson(jsonString, Stock.class);
        return stockParams;
    }

} 

