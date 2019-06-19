package com.acg12.widget.dialog.debug;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.acg12.R;

import java.util.List;

public class DebugServerDialogAdapter extends RecyclerView.Adapter<DebugServerDialogAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    private List<ServerUrl> dataValues;
    private Integer initPosisition;
    private int selectedPosition;

    public int getthisPosition() {
        return selectedPosition;
    }

    public void setSelectPosition(int thisPosition) {
        this.selectedPosition = thisPosition;
    }

    public void setSysPosition(int sysPosition) {
        if (initPosisition == null) {
            initPosisition = sysPosition;
        }
    }

    private OnItemClickListener onItemClickListener;

    public DebugServerDialogAdapter(List<ServerUrl> data) {
        this.dataValues = data;

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_debug_server_url, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position == getthisPosition()) {
            holder.tv.setBackgroundColor(Color.YELLOW);
        } else {
            holder.tv.setBackgroundColor(Color.WHITE);
        }

        if (initPosisition != null && position == initPosisition) {
            holder.tv.setTextColor(Color.RED);
        }

        ServerUrl url = dataValues.get(position);

        String txt = String.format("[%d]-%s", position, url.baseURL);

        holder.tv.setText(txt);

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.tv, holder.getLayoutPosition(), dataValues.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ServerUrl url);
    }
}
