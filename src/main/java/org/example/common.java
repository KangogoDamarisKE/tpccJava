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

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.DataType;


@DataType()
public class common {

    //public static final org.hyperledger.fabric.Logger logger = new Logger("tpcc");

    /**
     * Enumerates the tables of the TPC-C benchmark.
     * //@type {{WAREHOUSE: string, DISTRICT: string, CUSTOMER: string, HISTORY: string, NEW_ORDER: string, ORDER: string, ORDER_LINE: string, ITEM: string, STOCK: string}}
     */
    public class TABLES{
        final static String WAREHOUSE = "WAREHOUSE";
        final static String DISTRICT = "DISTRICT";
        final static String CUSTOMER = "CUSTOMER";
        final static String CUSTOMER_LAST_NAME = "CUSTOMER_LAST_NAME";
        final static String HISTORY = "HISTORY";
        final static String NEW_ORDER = "NEW_ORDER";
        final static String ORDERS = "ORDERS";
        final static String ORDER_LINE = "ORDER_LINE";
        final static String ITEM = "ITEM";
        final static String STOCK = "STOCK";
    }


    //Logs the given debug message by appending the TX ID stub before it.
    public static void log(String msg, Context ctx, String level) {

    }

    final static int padLength = Integer.toString(Integer.MAX_VALUE).length();
   
    /**
     * Converts the number to text and pads it to a fix length.
     * @param num The number to pad. 
     * @return The padded number text.
     */
    public static String pad(int num) {
        
        return String.format("%0" + padLength + "d", num);
    } 

    
}
