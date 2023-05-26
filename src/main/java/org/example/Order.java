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


package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class Order {
    @Property() 
    //The order ID. Primary key.
    public int o_id;

    @Property()
    // The district ID associated with the order. Primary key.
    public int o_d_id;

    @Property() 
    //The warehouse ID associated with the order. Primary key.
    public int o_w_id;

    @Property() 
    //The customer ID associated with the order.
    public int o_c_id;

    @Property() 
    //The date when the order was submitted.
    public String o_entry_d;

    @Property() 
    //The carrier ID associated with the order.
    public int o_carrier_id;

    @Property() 
    //The number of lines in the order.
    public int o_ol_cnt;

    @Property()
    // 1 if every order lines is local, otherwise 0.
    public int o_all_local;


    public Order(){
        
    }

}
