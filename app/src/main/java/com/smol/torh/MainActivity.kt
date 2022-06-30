package com.smol.torh


import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.smol.torh.ui.theme.TORHTheme
import net.freehaven.tor.control.TorControlConnection
import org.torproject.jni.TorService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TORHTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    //
                    Text("Hello!")


                    //
                    val av =
                        AndroidView(factory = {
                        ctx ->
                      WebView(ctx).apply {

                                settings.apply {
                              //      setLayerType(View.LAYER_TYPE_HARDWARE /*, paint?*/)
                                    setLoadsImagesAutomatically(true);
                                    setCacheMode(WebSettings.LOAD_NO_CACHE);
                                    setSupportMultipleWindows(false);
                                    getMediaPlaybackRequiresUserGesture();
                                    setJavaScriptEnabled(true);
                                    setSupportZoom(true);
                                    setUserAgentString("Mozilla/5.0 (Linux; Android 9; Unspecified Device) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.0.0 Mobile Safari/537.36");

                                    setAllowContentAccess(false);
                                    setAllowFileAccess(false);
                                    setDomStorageEnabled(false);

                                    clearCache(true); // a good practice to clear all cache to prevent loading issues
                                    clearFormData(); // a good practice to reset form data
                                    setScrollbarFadingEnabled(false); // forces the scrollbar to stay visible
                                    canGoBack();

                                    setOnKeyListener { view, i, keyEvent ->
                                        //key listener
                                        false
                                    }

                                    //register receiver ??
/*
                                    registerReceiver(object : BroadcastReceiver() {
                                        override fun onReceive(context: Context, intent: Intent) {
                                            val status =
                                                intent.getStringExtra(TorService.EXTRA_STATUS)
                                            Toast.makeText(context, status, Toast.LENGTH_SHORT)
                                                .show()
                                            loadUrl("https://shmishmorsh.github.io/StaticWeb/") //link to website you want to wrap
                                        }
                                    }, IntentFilter(TorService.ACTION_STATUS))
                                    */


                                    //bind service ??

                                    bindService(
                                        Intent(ctx, TorService::class.java),
                                        object : ServiceConnection {
                                            override fun onServiceConnected(
                                                name: ComponentName,
                                                service: IBinder
                                            ) {

                                                //moved torService to a local variable, since we only need it once
                                                val torService: TorService =
                                                    (service as TorService.LocalBinder).getService()
                                                var conn: TorControlConnection? =
                                                    torService.getTorControlConnection()
                                                while (torService.getTorControlConnection()
                                                        .also { conn = it } == null
                                                ) {
                                                    try {
                                                        Thread.sleep(500)
                                                    } catch (e: InterruptedException) {
                                                        e.printStackTrace()
                                                    }
                                                }
                                                if (conn != null) {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "Got TOR connection, please wait.",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }

                                            override fun onServiceDisconnected(name: ComponentName) {}
                                        },
                                        BIND_AUTO_CREATE
                                    )


                                }

                                        CookieManager.getInstance().setAcceptCookie(true)
                          CookieManager.getInstance().setAcceptThirdPartyCookies(this, false);










                    //
                    //nav bar?
                    //
                }
            })
        }
    }
}}}
