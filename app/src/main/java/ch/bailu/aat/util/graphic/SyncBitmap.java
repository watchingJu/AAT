package ch.bailu.aat.util.graphic;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.caverock.androidsvg.SVG;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.map.android.graphics.AndroidBitmap;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;

import java.io.Closeable;
import java.io.File;

import ch.bailu.aat.services.cache.ObjectHandle;

public class SyncBitmap implements Closeable {
    private Bitmap bitmap = null;
    private Drawable drawable = null;

    private long size = ObjectHandle.MIN_SIZE;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public synchronized android.graphics.Bitmap getAndroidBitmap() {
        if (bitmap != null) return AndroidGraphicFactory.getBitmap(bitmap);
        return null;
    }


    public synchronized Drawable getDrawable(Resources r) {
        if (drawable == null) {
            android.graphics.Bitmap bitmap = getAndroidBitmap();
            if (bitmap != null) {
                drawable = new BitmapDrawable(r, bitmap);
            }
        }
        return drawable;
    }



    public synchronized Canvas getAndroidCanvas() {
        android.graphics.Bitmap bitmap = getAndroidBitmap();
        if (bitmap != null) return new Canvas(bitmap);
        return null;
    }



    public static Bitmap load(File file) {
        android.graphics.Bitmap b =
                android.graphics.BitmapFactory.decodeFile(file.toString());

        return new AndroidBitmap(b);

    }

    public synchronized void set(File file) {
        set(load(file));
    }


    public synchronized void set(Bitmap b) {
        if (bitmap == b) return;
        free();
        bitmap = b;

        android.graphics.Bitmap bitmap = getAndroidBitmap();

        if (bitmap != null) {
            size = bitmap.getRowBytes() * bitmap.getHeight();
        } else {
            size = ObjectHandle.MIN_SIZE;
        }
    }

    public synchronized void set(int size, boolean transparent) {
        set(AndroidGraphicFactory.INSTANCE.createTileBitmap(size, transparent));
    }

    public synchronized void set(SVG svg, int size) {
        SyncTileBitmap b = new SyncTileBitmap();
        b.set(svg, size);
        set(b.getTileBitmap());
    }


    public synchronized long getSize() {
        return size;
    }



    public synchronized void free() {
        if (bitmap != null) {
            bitmap.decrementRefCount();
        }
        bitmap = null;
        drawable = null;
        size = ObjectHandle.MIN_SIZE;
    }


     @Override
    public void close()  {
        free();
    }
}
