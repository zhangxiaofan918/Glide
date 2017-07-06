package com.zhangxiaofan.glide;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvMain;
    //自定义缩略图
    private DrawableRequestBuilder<Integer> thumbnailRequest = Glide
            .with(this)
            .load(R.drawable.background);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvMain = (ImageView) findViewById(R.id.iv_main);
        loadingInternetPic();
    }

    /**
     * 加载网络图片,可以根据需要使用相关属性
     */
    private void loadingInternetPic() {
        Glide.with(this)
                //图片地址,还可以使用byte[],File,Integer,Uri参数
                .load(Constant.URL_SIMPLE_DRAWEEVIEW)
                //优先级高
                .priority(Priority.HIGH)
                //用原图的1/2作为缩略图
                // .thumbnail(0.5f)
                //使用自定义的缩略图
                .thumbnail(thumbnailRequest)
                //设置占位图
                .placeholder(R.drawable.background)
                //设置错误时显示的图
                .error(R.drawable.background)
                //设置淡入淡出效果
                .crossFade(500)
                //限制图片大小
                .override(250,250)
                //设置缩放模式  还有一种FitCenter
                .centerCrop()
                //禁止内存缓存
                .skipMemoryCache(true)
                //禁止磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //圆形裁剪,使用前需要首先添加glide-transformations依赖
                .bitmapTransform(new CenterCrop(this))
                //设置监听错误信息以及图片来源的等
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                //目标图片
                .into(mIvMain);
    }

    /**
     * 自定义Target
     */
    private void loadCustomTarget(){
        Glide.with(getApplicationContext())
                .load(Constant.URL_SIMPLE_DRAWEEVIEW)
                //想使用BitmapImageViewTarget必须要加这句
                .asBitmap()
                .into(new BitmapImageViewTarget(mIvMain) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);

                    }
                });
    }

    /**
     * 加载图片
     */
    private void loadingResPic() {
        Glide.with(this)
                .load(R.drawable.toys)
                .into(mIvMain);

//        Glide.with(this)
//                .load("file:///android_asset/toys.png")
//                .into(mIvMain);
    }

    /**
     * 清除缓存
     */
    private void clearCache(){
        //清除磁盘缓存
        Glide.get(this).clearDiskCache();
        //清除内存缓存
        Glide.get(this).clearMemory();
    }
}
