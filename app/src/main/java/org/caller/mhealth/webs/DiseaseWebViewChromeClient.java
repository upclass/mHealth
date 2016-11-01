package org.caller.mhealth.webs;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by xsm on 16-11-1.
 */

public class DiseaseWebViewChromeClient extends WebChromeClient {

    private BrowserSupport mSupport;
    public DiseaseWebViewChromeClient(BrowserSupport support) {
        mSupport = support;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mSupport != null) {
            mSupport.onProgressChanged(view, newProgress);
        }
    }

}
