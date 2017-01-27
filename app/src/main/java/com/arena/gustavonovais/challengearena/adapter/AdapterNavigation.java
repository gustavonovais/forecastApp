package com.arena.gustavonovais.challengearena.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arena.gustavonovais.challengearena.AdapterNavigationBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.model.NavObject;

import java.util.List;

public class AdapterNavigation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<NavObject> navObjectList;
    private OnItemSelectedListener listener;
    public AdapterNavigation(OnItemSelectedListener listener) {
        this.listener = listener;
        NavObject navObject = new NavObject();
        navObjectList = navObject.getList();
    }

    public AdapterNavigation(OnItemSelectedListener listener, NavObject object) {
        this.listener = listener;
        NavObject navObject = new NavObject();
        navObjectList = navObject.getList();
        navObjectList.add(object);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterNavigationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_navigation, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder myViewHolder = (ViewHolder) holder;
            myViewHolder.bindItem(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AdapterNavigationBinding binding;


        public ViewHolder(AdapterNavigationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        public void bindItem(final int position) {
            NavObject navObject = navObjectList.get(position);

            binding.imageViewIcon.setImageResource(navObject.getIcon());
            binding.txtTitle.setText(navObject.getTitle());
        }

        @Override
        public void onClick(View v) {
            int postition = getAdapterPosition();
            listener.onItemSelected(postition);
        }
    }

    @Override
    public int getItemCount() {
        return navObjectList.size();
    }


    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

}
