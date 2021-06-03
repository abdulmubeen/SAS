package com.batch08.srucms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<Model> imageModelArrayList;
    private Context ctx;

    interface OnItemCheckListener {
        void onItemCheck(Model item);
        void onItemUncheck(Model item);
    }
    @NonNull
    private OnItemCheckListener onItemCheckListener;

    public CustomAdapter(Context ctx, ArrayList<Model> imageModelArrayList, @NonNull OnItemCheckListener onItemCheckListener) {

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.student_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            final Model currentItem = imageModelArrayList.get(position);

            holder.checkBox.setChecked(imageModelArrayList.get(position).getSelected());
            holder.tvHno.setText(imageModelArrayList.get(position).getHno());
            holder.checkBox.setTag(position);

            ((MyViewHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyViewHolder) holder).checkBox.setChecked(
                            !((MyViewHolder) holder).checkBox.isChecked());
                    if (((MyViewHolder) holder).checkBox.isChecked()) {
                        onItemCheckListener.onItemCheck(currentItem);
                    } else {
                        onItemCheckListener.onItemUncheck(currentItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        protected CheckBox checkBox;
        private TextView tvHno;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            checkBox = (CheckBox) itemView.findViewById(R.id.stu_attendance_check);
            tvHno = (TextView) itemView.findViewById(R.id.card_roll_no);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
