/*
 * SPDX-License-Identifier: Apache License 2.0
 */

package org.example;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public final class TpccJavaContractTest {

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {

            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(new byte[] {});
            boolean result = contract.tpccJavaExists(ctx,"10001");

            assertFalse(result);
        }

        @Test
        public void assetExists() {

            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(new byte[] {42});
            boolean result = contract.tpccJavaExists(ctx,"10001");

            assertTrue(result);

        }

        @Test
        public void noKey() {
            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10002")).thenReturn(null);
            boolean result = contract.tpccJavaExists(ctx,"10002");

            assertFalse(result);

        }

    }

    @Nested
    class AssetCreates {

        @Test
        public void newAssetCreate() {
            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            String json = "{\"value\":\"TheTpccJava\"}";

            contract.createTpccJava(ctx, "10001", "TheTpccJava");

            verify(stub).putState("10001", json.getBytes(UTF_8));
        }

        @Test
        public void alreadyExists() {
            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10002")).thenReturn(new byte[] { 42 });

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createTpccJava(ctx, "10002", "TheTpccJava");
            });

            assertEquals(thrown.getMessage(), "The asset 10002 already exists");

        }

    }

    @Test
    public void assetRead() {
        TpccJavaContract contract = new  TpccJavaContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        TpccJava asset = new  TpccJava();
        asset.setValue("Valuable");

        String json = asset.toJSONString();
        when(stub.getState("10001")).thenReturn(json.getBytes(StandardCharsets.UTF_8));

        TpccJava returnedAsset = contract.readTpccJava(ctx, "10001");
        assertEquals(returnedAsset.getValue(), asset.getValue());
    }

    @Nested
    class AssetUpdates {
        @Test
        public void updateExisting() {
            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState("10001")).thenReturn(new byte[] { 42 });

            contract.updateTpccJava(ctx, "10001", "updates");

            String json = "{\"value\":\"updates\"}";
            verify(stub).putState("10001", json.getBytes(UTF_8));
        }

        @Test
        public void updateMissing() {
            TpccJavaContract contract = new  TpccJavaContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateTpccJava(ctx, "10001", "TheTpccJava");
            });

            assertEquals(thrown.getMessage(), "The asset 10001 does not exist");
        }

    }

    @Test
    public void assetDelete() {
        TpccJavaContract contract = new  TpccJavaContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getState("10001")).thenReturn(null);

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            contract.deleteTpccJava(ctx, "10001");
        });

        assertEquals(thrown.getMessage(), "The asset 10001 does not exist");
    }

}
