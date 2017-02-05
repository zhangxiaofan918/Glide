package com.zhangxiaofan.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvMain = (ImageView) findViewById(R.id.iv_main);

        //加载图片
        loadingInternetPic();
    }

    /**
     * 加载网络图片
     */
    private void loadingInternetPic(){
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(this)
                .load(R.drawable.background);

        Glide.with(this)
             .load(Constant.URL_SIMPLE_DRAWEEVIEW)          //图片地址,还可以使用byte[],File,Integer,Uri参数
             .priority(Priority.HIGH)                       //优先级高
//             .thumbnail(0.5f)                             //用原图的1/2作为缩略图
             .thumbnail(thumbnailRequest)                   //使用自定义的缩略图
//             .skipMemoryCache(true)                       //禁止内存缓存,使用Glide.get(context).clearMemory()清除缓存
//             .diskCacheStrategy(DiskCacheStrategy.NONE)   //禁止磁盘缓存,使用Glide.get(applicationContext).clearDiskCache();清除缓存
//             .bitmapTransform(new CenterCrop(this))       //圆形裁剪,使用前需要首先添加glide-transformations依赖
             .into(mIvMain);                                //目标图片
    }

    /**
     * 加载drawable图片
     */
    private void loadingResPic(){
        Glide.with(this)
             .load(R.drawable.toys)
             .into(mIvMain);
    }

    /**
     * 加载asset图片
     */
    private void loadingAssetPic(){
        Glide.with(this)
             .load("file:///android_asset/toys.png")
             .into(mIvMain);
    }
}
