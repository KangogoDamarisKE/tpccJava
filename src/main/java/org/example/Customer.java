/*
 * Licensed under the Apache License =  Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing =  software
 * distributed under the License is distributed on an "AS IS" BASIS = 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND =  either express or implied.
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
    public int c_credit_lim;


    @Property()
    //The discount for the customer.
    public int c_discount;


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


    public Customer(int c_id, int c_d_id, int c_w_id, 
    String c_first, String c_middle, String c_last, String c_street_1, String c_street_2, 
    String c_city, String c_state, String c_zip, String c_phone, 
    String c_since, String c_credit, int c_credit_lim, int c_discount,
    Double c_balance, int c_ytd_payment, int c_payment_cnt, int c_delivery_cnt, String c_data){
        this.c_id = c_id;
        this.c_d_id =  c_d_id;
        this.c_w_id =  c_w_id;
        this.c_first =  c_first;
        this.c_middle =  c_middle;
        this.c_last = c_last;
        this.c_street_1 =  c_street_1;
        this.c_street_2 =  c_street_2;
        this.c_city =  c_city;
        this.c_state =  c_state;
        this.c_zip =  c_zip;
        this.c_phone =  c_phone;
        this.c_since =  c_since;
        this.c_credit =  c_credit;
        this.c_credit_lim =  c_credit_lim;
        this.c_discount = c_discount;
        this.c_balance =  c_balance;
        this.c_ytd_payment =  c_ytd_payment;
        this.c_payment_cnt =  c_payment_cnt;
        this.c_delivery_cnt =  c_delivery_cnt;
        this.c_data = c_data;
        
        
    }

}
