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

import java.util.Date;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class DeliveryParameters {
    @Property()
    //The warehouse ID.
    public int w_id;

    @Property()
    //The carrier ID for the order.
    public int o_carrier_id;

    @Property()
    //The delivery date of the order.
    public Date ol_delivery_d;


    public DeliveryParameters(int w_id, int o_carrier_id, Date ol_delivery_d){
        this.w_id = w_id;
        this.o_carrier_id = o_carrier_id;
        this.ol_delivery_d = ol_delivery_d;
    }

}
