package com.bayazid.cpik_present_system.Network_Cheaker;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

import com.android.volley.toolbox.ImageLoader;


/**
 * Created by mb on 11/07/2016.
 */

public class BitmapCache extends LruCache<String, Bitmap> implements
        ImageLoader.ImageCache {

    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    BitmapCache() {
        this(getDefaultLruCacheSize());
    }

    private BitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

}
