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
public class History {

    @Property()
    //The customer ID. Primary key.
    public int h_c_id;


    @Property()
    //The district ID associated with the customer. Primary key.
    public int h_c_d_id;


    @Property()
    //The warehouse ID associated with the customer. Primary key.
    public int h_c_w_id;


    @Property()
    //The district ID.
    public int h_d_id;

    
    @Property()
    //The warehouse ID.
    public int h_w_id;


    @Property()
    //The date for the history. Primary key.
    public String h_date;


    @Property()
    //The amount of payment.
    public Double h_amount;


    @Property()
    //Arbitrary information.
    public String h_data;

    public History(){
        
    }

}
