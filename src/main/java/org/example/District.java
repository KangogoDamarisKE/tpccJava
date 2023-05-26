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
public class District {
    @Property()
    //The district ID. Primary key.
    public int d_id;


    @Property()
    //The warehouse ID associated with the district. Primary key.
    public int d_w_id;

    @Property()
    //The name of the district.
    public String d_name;

    @Property()
    //The first street name of the district.
    public String d_street_1;

    @Property()
    //The second street name of the district.
    public String d_street_2;

    @Property()
    //The city of the district. 
    public String d_city;

    @Property()
    //The state of the district.
    public String d_state;

    @Property()
    //The ZIP code of the district.
    public String d_zip;

    @Property()
    //The sales tax of the district.
    public int d_tax;

    @Property()
    //The year to date balance of the district.
    public int d_ytd;

    @Property()
    //The next available order ID.
    public int d_next_o_id;

    public District(){
        
    }

    // public District(int d_id, int d_w_id, String d_name, String d_street_1, String d_street_2, String d_city, String d_state, String d_zip, int d_tax, int d_ytd, int d_next_o_id){
    //     this.d_id = d_id;
    //     this.d_w_id = d_w_id;
    //     this.d_name = d_name;
    //     this.d_street_1 = d_street_1;
    //     this.d_street_2 = d_street_2;
    //     this.d_city = d_city;
    //     this.d_state = d_state;
    //     this.d_zip = d_zip;
    //     this.d_tax = d_tax;
    //     this.d_ytd = d_ytd;
    //     this.d_next_o_id = d_next_o_id;

    // }

}
