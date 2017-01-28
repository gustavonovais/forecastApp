package com.arena.gustavonovais.challengearena.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arena.gustavonovais.challengearena.AdapterNextDaysBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.enums.IconEnum;
import com.arena.gustavonovais.challengearena.model.Data;
import com.arena.gustavonovais.challengearena.utils.ActivityUtils;
import com.arena.gustavonovais.challengearena.utils.DateUtils;

import java.util.ArrayList;

public class AdapterNextDays extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final ArrayList<Data> dataList;

    public AdapterNextDays(ArrayList<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterNextDaysBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_next_days, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder myViewHolder = (ViewHolder) holder;
            myViewHolder.bindItem(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final AdapterNextDaysBinding binding;


        public ViewHolder(AdapterNextDaysBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindItem(final int position) {
            Data data = dataList.get(position);

            binding.txtSummary.setText(data.summary);
            binding.txtTemperature.setText(String.valueOf(data.temperatureMax + ActivityUtils.FAHRENHEIT));
            binding.imgIcon.setImageDrawable(IconEnum.fromDesc(data.icon));
            binding.txtDate.setText(DateUtils.converDate(data.time));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
