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
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class Customer {
    //The customer ID. Primary key.
    @Property()
    public int c_id;


    @Property()
    //The district ID associated with the customer. Primary key.
    public int c_d_id;


    @Property()
    //The warehouse ID associated with the customer. Primary key.
    public int c_w_id;


    @Property() //The first name of the customer.
    public String c_first;


    @Property() //The middle name of the customer.
    public String c_middle;


    @Property() 
    //The last name of the customer.
    public String c_last;


    @Property()
    //The first street name of the customer.
    public String c_street_1;


    @Property()
    //The second street name of the customer.
    public String c_street_2;


    @Property()
    //The city of the customer.
    public String c_city;


    @Property()
    //The state of the customer.
    public String c_state;


    @Property()
    //The ZIP code of the customer.
    public String c_zip;


    @Property()
    //The phone number of the customer
    public String c_phone;


    @Property()
    //The date when the customer was registered.
    public String c_since;


    @Property()
    //The credit classification of the customer (GC or BC).
    public String c_credit;


    @Property()
    //The credit limit of the customer.
    public Double c_credit_lim;


    @Property()
    //The discount for the customer.
    public Double c_discount;


    @Property()
    //The balance of the customer.
    public Double c_balance;


    @Property()
    //The year to date payment of the customer.
    public int c_ytd_payment;


    @Property()
    //The number of times the customer paid.
    public int c_payment_cnt;


    @Property()
    //The number of times a delivery was made for the customer.
    public int c_delivery_cnt;


    @Property()
    //Arbitrary information.
    public String c_data;


    public Customer(){
        
        
    }

}
