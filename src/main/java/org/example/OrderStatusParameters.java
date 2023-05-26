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
public class OrderStatusParameters {
   
   @Property()
   //The warehouse ID.
   public int w_id;

   @Property()
   //The district ID.
   public int d_id;

   @Property()
   //The customer ID if provided.
   public int c_id;


   @Property()
   //The last name of the customer if provided.
   public String c_last;


   public OrderStatusParameters(){
      
   }

   // public OrderStatusParameters(int w_id, int d_id, int c_id, String c_last){
   //    this.w_id = w_id;
   //    this.d_id = d_id;
   //    this.c_id = c_id;
   //    this.c_last = c_last;
   // }    
 
 }