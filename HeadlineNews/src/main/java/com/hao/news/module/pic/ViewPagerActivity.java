package com.hao.news.module.pic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.hao.news.R;
import com.hao.news.model.ItemModel;
import com.hao.news.network.HttpUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

public class ViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    private PagerAdapter adapter;
    private int position;
    private static List<ItemModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

        }
        //没有状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new MPagerAdapter();
        viewPager.setAdapter(adapter);
        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            modelList = getIntent().getParcelableArrayListExtra("picList");
            viewPager.setCurrentItem(position);
            adapter.notifyDataSetChanged();
        }
    }

    static class MPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return modelList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ItemModel itemModel = modelList.get(position);
            setImg(photoView, itemModel.imageUrl);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        //设置图片
        private void setImg(final PhotoView photoView, String url) {
            HttpUtils.getBitmap()
                    .getBitmapFromNet(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<ResponseBody, Bitmap>() {
                        @Override
                        public Bitmap call(ResponseBody responseBody) {
                            InputStream inputStream = responseBody.byteStream();
                            return BitmapFactory.decodeStream(inputStream);
                        }
                    })
                    .subscribe(new Action1<Bitmap>() {
                        @Override
                        public void call(Bitmap bitmap) {
                            photoView.setImageBitmap(bitmap);
                        }
                    });
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
