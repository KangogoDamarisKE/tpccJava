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
public class PaymentParameters {

    @Property()
    //The warehouse ID.
    public int w_id;


    @Property()
    //The district ID
    public int d_id;


    @Property()
    //The  The payment amount.
    public Double h_amount;


    @Property()
    //The warehouse ID to which the customer belongs to.
    public int c_w_id;


    @Property()
    //The district ID to which the customer belongs to.
    public int c_d_id;

   
    @Property()
    //The customer ID.
    public int c_id;


    @Property()
    //The last name of the customer.
    public String c_last;


    @Property()
    //The payment date.
    public String h_date;


    public PaymentParameters(){
        
    }

    // public PaymentParameters(int w_id, int d_id, int h_amount, int c_w_id, int c_d_id, int c_id, String c_last, String h_date){
    //     this.w_id = w_id;
    //     this.d_id = d_id;
    //     this.h_amount = h_amount;
    //     this.c_w_id = c_w_id;
    //     this.c_d_id = c_d_id;
    //     this.c_id = c_id;
    //     this.c_last = c_last;
    //     this.h_date = h_date;
    // }

}
