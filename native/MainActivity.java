package io.github.bibomoglal.brightstar;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(DownloaderPlugin.class);
        registerPlugin(SecureStorePlugin.class);
        super.onCreate(savedInstanceState);
    }
}
