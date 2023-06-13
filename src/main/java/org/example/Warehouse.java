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


@DataType
public class Warehouse {
    @Property()
    //The warehouse ID. Primary key.
    public int w_id;

    @Property()
    //The name of the warehouse.
    public String w_name;

    @Property()
    //The first street name of the warehouse.
    public String w_street_1;

    @Property()
    //The second street name of the warehouse.
    public String w_street_2;

    @Property()
    //The city of the warehouse.
    public String w_city;

    @Property()
    //The state of the warehouse.
    public String w_state;

    @Property()
    //The ZIP code of the warehouse.
    public String w_zip;

    @Property()
    //The sales tax of the warehouse.
    public Double w_tax;

    @Property()
    //The year to date balance of the warehouse.
    public int w_ytd;

    public Warehouse(){

    }

}
