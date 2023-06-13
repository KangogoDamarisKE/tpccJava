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

//import java.util.Date;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class OrderLine {
    
    public OrderLine(){

    }


    @Property()
    //The order ID associated with the order line. Primary key.
    public int ol_o_id;

    @Property()
    //The district ID associated with the order line. Primary key.
    public int ol_d_id;

    @Property()
    //The warehouse ID associated with the order line. Primary key.
    public int ol_w_id;
    
    @Property()
    //The number/position/index of the order line. Primary key.
    public int ol_number;

    @Property()
    //The item ID associated with the order line.
    public int ol_i_id;

    @Property()
    //The ID of the supplying warehouse.
    public int ol_supply_w_id;

    @Property()
    //The date of delivery.
    public String ol_delivery_d;

    @Property()
    //The quantity of items in the order line.
    public int ol_quantity;

    @Property()
    //The amount to pay.
    public Double ol_amount;

    @Property()
    //Information about the district.
    public String ol_dist_info;


}
