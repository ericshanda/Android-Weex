package com.taobao.demo.orange.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.taobao.orange.model.ConfigDO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wuer on 2018/5/21.
 */

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ViewHolder> implements View.OnClickListener {
    private List<ConfigDO> mData = new ArrayList<>();

    public ConfigAdapter(Collection<ConfigDO> data) {
        mData.addAll(data);
    }

    public void update(Collection<ConfigDO> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public ConfigDO get(int position) {
        if (position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_config, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.nameText.setText(mData.get(position).name);
        holder.levelText.setText(mData.get(position).level);
        holder.typeText.setText(mData.get(position).type);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView levelText;
        TextView typeText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name);
            levelText = (TextView) itemView.findViewById(R.id.level);
            typeText = (TextView) itemView.findViewById(R.id.type);
        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
