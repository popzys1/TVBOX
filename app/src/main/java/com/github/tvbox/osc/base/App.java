package com.github.tvbox.osc.base;

<<<<<<< HEAD
import android.os.Environment;

=======
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.multidex.MultiDex;
>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
import androidx.multidex.MultiDexApplication;

import com.github.catvod.crawler.JarLoader;
import com.github.catvod.crawler.JsLoader;
<<<<<<< HEAD
import com.github.tvbox.osc.R;
=======
import com.github.tvbox.osc.BuildConfig;
>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
import com.github.tvbox.osc.callback.EmptyCallback;
import com.github.tvbox.osc.callback.LoadingCallback;
import com.github.tvbox.osc.data.AppDataManager;
import com.github.tvbox.osc.server.ControlManager;
import com.github.tvbox.osc.util.EpgUtil;
import com.github.tvbox.osc.util.FileUtils;
import com.github.tvbox.osc.util.FixDexUtils;
import com.github.tvbox.osc.util.HawkConfig;
import com.github.tvbox.osc.util.LocaleHelper;
import com.github.tvbox.osc.util.LOG;
import com.github.tvbox.osc.util.OkGoHelper;
import com.github.tvbox.osc.util.PlayerHelper;
import com.github.tvbox.osc.util.SubtitleHelper;
import com.hjq.permissions.XXPermissions;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.hawk.Hawk;

import com.p2p.P2PClass;
import com.whl.quickjs.android.QuickJSLoader;
<<<<<<< HEAD

import java.io.File;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
=======
import org.conscrypt.Conscrypt;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;

>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * @author pj567
 * @date :2020/12/17
 * @description:
 */
public class App extends MultiDexApplication {
    private static App instance;
    private static P2PClass p;
    public static String burl;
    private static String dashData;
<<<<<<< HEAD
    public static ViewPump viewPump = null;


=======
    public static Provider conscrypt = Conscrypt.newProvider();
>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SubtitleHelper.initSubtitleColor(this);
        initParams();
        // takagen99 : Initialize Locale
        initLocale();
        // OKGo
        OkGoHelper.init();
        // 闭关检查模式
        XXPermissions.setCheckMode(false);
        // Get EPG Info
        EpgUtil.init();
        // 初始化Web服务器
        ControlManager.init(this);
        //初始化数据库
        AppDataManager.init();
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .commit();
        AutoSizeConfig.getInstance().setCustomFragment(true).getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
        PlayerHelper.init();

        // Delete Cache
        /*File dir = getCacheDir();
        FileUtils.recursiveDelete(dir);
        dir = getExternalCacheDir();
        FileUtils.recursiveDelete(dir);*/

        FileUtils.cleanPlayerCache();

        // Add JS support
        QuickJSLoader.init();
<<<<<<< HEAD

        // add font support, my tv embed font not include emoji
        String extStorageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File fontFile = new File(extStorageDir + "/tvbox.ttf");
        if (fontFile.exists()) {
            viewPump = ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setDefaultFontPath(fontFile.getAbsolutePath())
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build();
        }
=======
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Security.insertProviderAt(conscrypt, 1);
        }

>>>>>>> parent of a63f6f8 (修复web嗅探解析错误)
    }

    public static P2PClass getp2p() {
        try {
            if (p == null) {
                p = new P2PClass(FileUtils.getExternalCachePath());
            }
            return p;
        } catch (Exception e) {
            LOG.e(e.toString());
            return null;
        }
    }


    private void initParams() {
        // Hawk
        Hawk.init(this).build();
        Hawk.put(HawkConfig.DEBUG_OPEN, false);

        // 首页选项
        putDefault(HawkConfig.HOME_SHOW_SOURCE, true);       //数据源显示: true=开启, false=关闭
        putDefault(HawkConfig.HOME_SEARCH_POSITION, false);  //按钮位置-搜索: true=上方, false=下方
        putDefault(HawkConfig.HOME_MENU_POSITION, true);     //按钮位置-设置: true=上方, false=下方
        putDefault(HawkConfig.HOME_REC, 1);                  //推荐: 0=豆瓣热播, 1=站点推荐, 2=观看历史
        putDefault(HawkConfig.HOME_NUM, 4);                  //历史条数: 0=20条, 1=40条, 2=60条, 3=80条, 4=100条
        // 播放器选项
        putDefault(HawkConfig.SHOW_PREVIEW, true);           //窗口预览: true=开启, false=关闭
        putDefault(HawkConfig.PLAY_SCALE, 0);                //画面缩放: 0=默认, 1=16:9, 2=4:3, 3=填充, 4=原始, 5=裁剪
        putDefault(HawkConfig.BACKGROUND_PLAY_TYPE, 0);      //后台：0=关闭, 1=开启, 2=画中画
        putDefault(HawkConfig.PLAY_TYPE, 1);                 //播放器: 0=系统, 1=IJK, 2=Exo, 3=MX, 4=Reex, 5=Kodi
        putDefault(HawkConfig.IJK_CODEC, "硬解码");           //IJK解码: 软解码, 硬解码
        // 系统选项
        putDefault(HawkConfig.HOME_LOCALE, 0);               //语言: 0=中文, 1=英文
        putDefault(HawkConfig.THEME_SELECT, 0);              //主题: 0=奈飞, 1=哆啦, 2=百事, 3=鸣人, 4=小黄, 5=八神, 6=樱花
        putDefault(HawkConfig.SEARCH_VIEW, 1);               //搜索展示: 0=文字列表, 1=缩略图
        putDefault(HawkConfig.PARSE_WEBVIEW, true);          //嗅探Webview: true=系统自带, false=XWalkView
        putDefault(HawkConfig.DOH_URL, 0);                   //安全DNS: 0=关闭, 1=腾讯, 2=阿里, 3=360, 4=Google, 5=AdGuard, 6=Quad9

    }

    private void initLocale() {
        if (Hawk.get(HawkConfig.HOME_LOCALE, 0) == 0) {
            LocaleHelper.setLocale(App.this, "zh");
        } else {
            LocaleHelper.setLocale(App.this, "");
        }
    }

    public static App getInstance() {
        return instance;
    }

    private void putDefault(String key, Object value) {
        if (!Hawk.contains(key)) {
            Hawk.put(key, value);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        JsLoader.load();
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);

        //okhttp的改动见OkHttpClientReplace.java的public OkHttpClientReplace build()
        //用于替换spider jar里的Builder.build()以让android 9及以下系统支持 tls 1.3
        try {
            Uri uri = Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+"/raw/okhttp_inject.dex");
            FixDexUtils.copy(base, new FileInputStream(uri.getPath()));
        } catch (IOException e) {
        }
        // 每次启动应用都先进行修复包加载操作
        FixDexUtils.loadDex(base);

        super.attachBaseContext(base);

    }

    public void setDashData(String data) {
        dashData = data;
    }

    public String getDashData() {
        return dashData;
    }
}
