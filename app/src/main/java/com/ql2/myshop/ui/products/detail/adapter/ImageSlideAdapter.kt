package com.ql2.myshop.ui.products.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ql2.myshop.R
import com.ql2.myshop.utils.AssetUtils
import timber.log.Timber

internal class ImageSlideAdapter(private val context: Context,
                                 private var imageList: List<String>
) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater).inflate(R.layout.image_slider_item, null)
        val ivImages = view.findViewById<AppCompatImageView>(R.id.iv_images)
        Timber.d(">>$imageList")
        AssetUtils.loadImageFromAssets(context = context,
            fileName = imageList[position], ivImages)

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}