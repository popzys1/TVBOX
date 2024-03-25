package com.github.catvod.net;

<<<<<<< HEAD
import android.net.Uri;

import androidx.collection.ArrayMap;

import com.github.tvbox.osc.bean.Doh;
import com.github.catvod.utils.Path;
import com.github.catvod.utils.Util;
import com.github.tvbox.osc.util.OkGoHelper;
import com.google.common.net.HttpHeaders;
=======
import android.util.ArrayMap;
import com.github.tvbox.osc.util.OkGoHelper;
import com.github.tvbox.osc.util.urlhttp.BrotliInterceptor;
>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.dnsoverhttps.DnsOverHttps;

public class OkHttp {

    private static final int TIMEOUT = 10 * 1000;
    private static final int CACHE = 50 * 1024 * 1024;    
    private OkHttpClient client;
    private OkHttpClient noRedirect;

    private static class Loader {
        static volatile OkHttp INSTANCE = new OkHttp();
    }    
    
    public static OkHttp get() {
        return Loader.INSTANCE;
    }

<<<<<<< HEAD
    public static Dns dns() {
//        return get().dns != null ? get().dns : Dns.SYSTEM; // 由于 setDoh(Doh doh)没有被调用导致这里选择的是 Dns.SYSTEM
        return get().dns != null ? get().dns : OkGoHelper.dnsOverHttps;
    }

    public void setDoh(Doh doh) {
        OkHttpClient dohClient = new OkHttpClient.Builder().cache(new Cache(Path.doh(), CACHE)).hostnameVerifier(SSLCompat.VERIFIER).sslSocketFactory(new SSLCompat(), SSLCompat.TM).build();
        dns = doh.getUrl().isEmpty() ? null : new DnsOverHttps.Builder().client(dohClient).url(HttpUrl.get(doh.getUrl())).bootstrapDnsHosts(doh.getHosts()).build();
        client = null;
    }

    public void setProxy(String proxy) {
        ProxySelector.setDefault(selector());
        selector().setProxy(proxy);
        client = null;
    }

    public static ProxySelector selector() {
        if (get().selector != null) return get().selector;
        return get().selector = new ProxySelector();
    }

=======
>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
    public static OkHttpClient client() {
        if (get().client != null) return get().client;
        return get().client = client(TIMEOUT);
    }

    public static OkHttpClient noRedirect() {
        if (get().noRedirect != null) return get().noRedirect;
        return get().noRedirect = client().newBuilder().followRedirects(false).followSslRedirects(false).build();
    }

    public static Dns dns() {
        return OkGoHelper.dnsOverHttps != null ? OkGoHelper.dnsOverHttps : Dns.SYSTEM;
    }

    public static OkHttpClient client(int timeout) {
        return new OkHttpClient.Builder().connectionSpecs(OkGoHelper.getConnectionSpec()).addInterceptor(new BrotliInterceptor()).connectTimeout(timeout, TimeUnit.MILLISECONDS).readTimeout(timeout, TimeUnit.MILLISECONDS).writeTimeout(timeout, TimeUnit.MILLISECONDS).dns(dns()).hostnameVerifier(SSLSocketFactoryCompat.hostnameVerifier).sslSocketFactory(new SSLSocketFactoryCompat(), SSLSocketFactoryCompat.trustAllCert).build();
    }

    public static Call newCall(String url) {
        return client().newCall(new Request.Builder().url(url).build());
    }

    public static Call newCall(OkHttpClient client, String url) {
        return client.newCall(new Request.Builder().url(url).build());
    }

    public static Call newCall(String url, Headers headers) {
        return client().newCall(new Request.Builder().url(url).headers(headers).build());
    }

    public static Call newCall(String url, ArrayMap<String, String> params) {
        return client().newCall(new Request.Builder().url(buildUrl(url, params)).build());
    }

    public static Call newCall(OkHttpClient client, String url, RequestBody body) {
        return client.newCall(new Request.Builder().url(url).post(body).build());
    }

    private static HttpUrl buildUrl(String url, ArrayMap<String, String> params) {
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) builder.addQueryParameter(entry.getKey(), entry.getValue());
        return builder.build();
    }
}
