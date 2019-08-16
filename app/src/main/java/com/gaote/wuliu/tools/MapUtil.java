package com.gaote.wuliu.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.gaote.wuliu.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapUtil {

    /**
     * 往地图上添加marker
     */
    public static Marker setMarker(Context context, AMap aMap, LatLng latlng,String pic) {
        Marker marker=null;
        if (aMap != null) {
            View view = View.inflate(context, R.layout.marker_layout, null);
            CircleImageView imageView = (CircleImageView) view.findViewById(R.id.iv_head);
            if(TextUtils.isEmpty(pic)){
                imageView.setImageResource(R.drawable.latticepoint);
            }else{
                Glide.with(context).load(pic).into(imageView);
            }

            Bitmap bitmap = convertViewToBitmap(view);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latlng)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
             marker = aMap.addMarker(markerOptions);
        }
        return marker;
    }
    public static Marker setPaperMarker(Context context, AMap aMap, LatLng latlng) {
        Marker marker=null;
        if (aMap != null) {
            View view = View.inflate(context, R.layout.marker_paper_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_head);
            imageView.setImageResource(R.mipmap.gt_paper);
            Bitmap bitmap = convertViewToBitmap(view);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latlng)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            marker = aMap.addMarker(markerOptions);
        }
        return marker;
    }
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
