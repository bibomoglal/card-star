package io.github.bibomoglal.brightstar;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

// Keeps small secrets (the router password) encrypted with a key that lives in the Android Keystore.
// The encrypted value is stored in private SharedPreferences, so it is never readable as plain text.
@CapacitorPlugin(name = "SecureStore")
public class SecureStorePlugin extends Plugin {
    private static final String ALIAS = "bs_secure_key";
    private static final String PREFS = "bs_secure";

    private SecretKey key() throws Exception {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        if (!ks.containsAlias(ALIAS)) {
            KeyGenerator kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            kg.init(new KeyGenParameterSpec.Builder(ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build());
            return kg.generateKey();
        }
        return (SecretKey) ks.getKey(ALIAS, null);
    }

    private SharedPreferences prefs() {
        return getContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    @PluginMethod
    public void set(PluginCall call) {
        String k = call.getString("key");
        String v = call.getString("value");
        if (k == null || v == null) {
            call.reject("missing key or value");
            return;
        }
        try {
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, key());
            byte[] iv = c.getIV();
            byte[] ct = c.doFinal(v.getBytes(StandardCharsets.UTF_8));
            String packed = Base64.encodeToString(iv, Base64.NO_WRAP) + ":" + Base64.encodeToString(ct, Base64.NO_WRAP);
            prefs().edit().putString(k, packed).apply();
            call.resolve();
        } catch (Exception e) {
            call.reject(String.valueOf(e.getMessage()));
        }
    }

    @PluginMethod
    public void get(PluginCall call) {
        String k = call.getString("key");
        if (k == null) {
            call.reject("missing key");
            return;
        }
        String out = "";
        String packed = prefs().getString(k, null);
        if (packed != null) {
            try {
                String[] p = packed.split(":");
                byte[] iv = Base64.decode(p[0], Base64.NO_WRAP);
                byte[] ct = Base64.decode(p[1], Base64.NO_WRAP);
                Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
                c.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(128, iv));
                out = new String(c.doFinal(ct), StandardCharsets.UTF_8);
            } catch (Exception e) {
                // key was lost (restore on another phone, lock-screen reset...): forget the value
                prefs().edit().remove(k).apply();
                out = "";
            }
        }
        JSObject r = new JSObject();
        r.put("value", out);
        call.resolve(r);
    }

    @PluginMethod
    public void remove(PluginCall call) {
        String k = call.getString("key");
        if (k != null) prefs().edit().remove(k).apply();
        call.resolve();
    }
}
