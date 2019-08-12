package com.gaote.wuliu.ui.client.wuliu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.wuliu.ChooseCompanyActivity;
import com.gaote.wuliu.ui.client.wuliu.model.ExpressCompanyItem;

import java.util.List;

import cn.ittiger.indexlist.adapter.IndexStickyViewAdapter;

public class MyIndexStickyViewAdapter extends IndexStickyViewAdapter<ExpressCompanyItem> {
    private Context context;
    public MyIndexStickyViewAdapter(List<ExpressCompanyItem> list) {
        super(list);
    }
    public MyIndexStickyViewAdapter(List<ExpressCompanyItem> list, Context context){
        this(list);
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateIndexViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_index, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_express_company, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindIndexViewHolder(RecyclerView.ViewHolder holder, int position, String indexName) {

        IndexViewHolder indexViewHolder = (IndexViewHolder) holder;
        indexViewHolder.mTextView.setText(indexName);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position, ExpressCompanyItem itemData) {

        ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
        contentViewHolder.mName.setText(itemData.getName());
        Glide.with(context).load(itemData.getPicUrl()).into(contentViewHolder.mAvatar);
        //CommonImageLoader.displayImage(itemData.getPicUrl(), contentViewHolder.mAvatar, CommonImageLoader.NO_CACHE_OPTIONS);
    }
    class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public IndexViewHolder(View itemView) {

            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        ImageView mAvatar;

        public ContentViewHolder(View itemView) {

            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
