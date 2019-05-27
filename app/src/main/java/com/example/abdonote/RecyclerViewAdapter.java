/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdonote.Model.ClassDate;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.View_Holder1> {
    private ArrayList<ClassDate> values1;
    private Context context;

    RecyclerViewAdapter(Context context, ArrayList<ClassDate> valu) {
        this.context = context;
        this.values1 = valu;
    }

    @Override
    public View_Holder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new View_Holder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_recyclerv, parent, false));

    }

    @Override
    public void onBindViewHolder(View_Holder1 holder, final int position) {

        final ClassDate sClassDate = values1.get(position);
        holder.mTitle.setText(sClassDate.getTitle());
        holder.mNote.setText(sClassDate.getNote());
        holder.mDate.setText(sClassDate.getTime());

        holder.layout_data_racyclerV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Edit_ItemRecylerV.class);
                Bundle db = new Bundle();
                db.putString("id", sClassDate.getId());
                db.putString("time", sClassDate.getTime());
                db.putString("note", sClassDate.getNote());
                db.putString("title", sClassDate.getTitle());
                intent.putExtras(db);
                context.startActivity(intent);

//                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return values1.size();
    }

    class View_Holder1 extends RecyclerView.ViewHolder {
        private LinearLayout layout_data_racyclerV;
        private TextView mTitle, mNote, mDate;


        View_Holder1(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_recyclerV);
            mNote = itemView.findViewById(R.id.note_recyclerV);
            mDate = itemView.findViewById(R.id.time_recyclerV);
            layout_data_racyclerV = itemView.findViewById(R.id.layout_data_racyclerV);
        }
    }
}



