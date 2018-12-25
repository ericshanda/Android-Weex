package com.taobao.demo.orange.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.taobao.orange.OCandidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wuer on 2018/5/21.
 */

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {
    private List<OCandidate> mData = new ArrayList<>();

    public CandidateAdapter(Collection<OCandidate> data) {
        mData.addAll(data);
    }

    public void update(Collection<OCandidate> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidate, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.keyText.setText(mData.get(position).getKey());
        holder.valueText.setText(mData.get(position).getClientVal());

        String compare = mData.get(position).getCompareClz();
        holder.compareText.setText(compare.substring(compare.lastIndexOf(".") + 1));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyText;
        TextView valueText;
        TextView compareText;

        public ViewHolder(View itemView) {
            super(itemView);
            keyText = (TextView) itemView.findViewById(R.id.key);
            valueText = (TextView) itemView.findViewById(R.id.value);
            compareText = (TextView) itemView.findViewById(R.id.compare);
        }
    }
}
