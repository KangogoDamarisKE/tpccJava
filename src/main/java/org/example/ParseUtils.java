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

import org.hyperledger.fabric.contract.annotation.DataType;
import com.google.gson.Gson;

@DataType
public class ParseUtils {
    private final static Gson gson = new Gson();

    //ObjectMapper objectMapper = new ObjectMapper();
        
    /**
     * Parses the parameters of a New Order TX. (To enable IDE code completion)
     * @param params The JSON string of the parameters.
     * @return The parsed parameters.
     */
    public static NewOrderParameters parseNewOrderParameters(String params) throws Exception {
        //return objectMapper.readValue(params, NewOrderParameters.class);
        return gson.fromJson(params, NewOrderParameters.class);
    }

    /**
     * Parses the parameters of a Payment TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {PaymentParameters} The parsed parameters.
     */
    public static PaymentParameters parsePaymentParameters(String params) throws Exception {
        //return objectMapper.readValue(params, PaymentParameters.class);
        return gson.fromJson(params, PaymentParameters.class);
    }

    /**
     * Parses the parameters of a Delivery TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {DeliveryParameters} The parsed parameters.
     */
    public static DeliveryParameters parseDeliveryParameters(String params) throws Exception {
        //return objectMapper.readValue(params, DeliveryParameters.class);
        return gson.fromJson(params, DeliveryParameters.class);
    }

    /**
     * Parses the parameters of an Order Status TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {OrderStatusParameters} The parsed parameters.
     */
    public static OrderStatusParameters parseOrderStatusParameters(String params) throws Exception {
        //return objectMapper.readValue(params, OrderStatusParameters.class);
        return gson.fromJson(params, OrderStatusParameters.class);
    }

    /**
     * Parses the parameters of an Order Status TX. (To enable IDE code completion)
     * @param {string} params The JSON string of the parameters.
     * @return {StockLevelParameters} The parsed parameters.
     */
    public static StockLevelParameters parseStockLevelParameters(String params) throws Exception {
        //return objectMapper.readValue(params, StockLevelParameters.class);
        return gson.fromJson(params, StockLevelParameters.class);
    }

    /**
     * Parses the Warehouse entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the warehouse.
     * @return {Warehouse} The warehouse object.
     */
    public static Warehouse parseWarehouse(String jsonString) {
        return gson.fromJson(jsonString, Warehouse.class);
    }

        /**
     * Parses the District entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the district.
     * @return {District} The district object.
     */
    public static District parseDistrict(String jsonString) {
        return gson.fromJson(jsonString, District.class);
    }

    /**
     * Parses the Customer entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the customer.
     * @return {Customer} The customer object.
     */
    public static Customer parseCustomer(String jsonString) {
        return gson.fromJson(jsonString, Customer.class);
    }

    /**
     * Parses the History entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the history.
     * @return {History} The history object.
     */
    public static History parseHistory(String jsonString) {
        return gson.fromJson(jsonString, History.class);
    }

    /**
     * Parses the New Order entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the new order.
     * @return {NewOrder} The new order object.
     */
    public static NewOrder parseNewOrder(String jsonString) {
        return gson.fromJson(jsonString, NewOrder.class);
    }

    /**
     * Parses the Order entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the order.
     * @return {Order} The order object.
     */     
    public static Order parseOrder(String jsonString) {
        return gson.fromJson(jsonString, Order.class);
    }

    /**
     * Parses the Order Line entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the order line.
     * @return {OrderLine} The order line object.
     */
    public static OrderLine parseOrderLine(String jsonString) {
        return gson.fromJson(jsonString, OrderLine.class);
    }

    /**
     * Parses the Item entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the item.
     * @return {Item} The item object.
     */
    public static Item parseItem(String jsonString) {
    return gson.fromJson(jsonString, Item.class);
    }

    /**
     * Parses the Stock entry JSON string. (To enable IDE code completion)
     * @param {string} jsonString The JSON string of the stock.
     * @return {Stock} The stock object.
     */
    public static Stock parseStock(String jsonString) {
        return gson.fromJson(jsonString, Stock.class);
    }

} 

