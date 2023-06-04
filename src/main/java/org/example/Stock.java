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
public class Stock {
   
    @Property()
    //The ID of the item associated with the stock. Primary key.
    public int s_i_id;

    @Property()
    //The ID of the warehouse associated with the stock. Primary key.
    public int s_w_id;

    @Property()
    //The quantity of the related item.
    public int s_quantity;

    @Property()
    //Information about district 1.
    public String s_dist_01;

    @Property()
    //Information about district 2.
    public String s_dist_02;

    @Property()
    //Information about district 3.
    public String s_dist_03;
   
    @Property()
    //Information about district 4.
    public String s_dist_04;

    @Property()
    //Information about district 5.
    public String s_dist_05;

    @Property()
    //Information about district 6.
    public String s_dist_06;

    @Property()
    //Information about district 7.
    public String s_dist_07;

    @Property()
    //Information about district 8.
    public String s_dist_08;

    @Property()
    //Information about district 9.
    public String s_dist_09;

    @Property()
    //Information about district 10.
    public String s_dist_10;
    
    @Property()
    //The year to date balance of the stock.
    public int s_ytd;

    @Property()
    //The number of orders for the stock.
    public int s_order_cnt;

    @Property()
    //The number of remote orders for the stock.
    public int s_remote_cnt;

    @Property()
    //Stock information.
    public String s_data;

    public void stock(){
        
    }

    public Stock(int s_i_id, int s_w_id, int s_quantity, String s_dist_01, String s_dist_02, 
    String s_dist_03, String s_dist_04, String s_dist_05, String s_dist_06, String s_dist_07, 
    String s_dist_08, String s_dist_09, String s_dist_10, int s_ytd, int s_order_cnt, 
    int s_remote_cnt, String s_data){
        this.s_i_id = s_i_id;
        this.s_w_id = s_w_id;
        this.s_quantity = s_quantity;
        this.s_dist_01 = s_dist_01;
        this.s_dist_02 = s_dist_02;
        this.s_dist_03 = s_dist_03;
        this.s_dist_04 = s_dist_04;
        this.s_dist_05 = s_dist_05;
        this.s_dist_06 = s_dist_06;
        this.s_dist_07 = s_dist_07;
        this.s_dist_08 = s_dist_08;
        this.s_dist_09 = s_dist_09;
        this.s_dist_10 = s_dist_10;
        this.s_ytd = s_ytd;
        this.s_order_cnt = s_order_cnt;
        this.s_remote_cnt = s_remote_cnt;
        this.s_data = s_data;        

    }

}
