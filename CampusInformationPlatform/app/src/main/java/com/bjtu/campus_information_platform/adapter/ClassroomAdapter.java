package com.bjtu.campus_information_platform.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bjtu.campus_information_platform.R;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> {
    private List<String> classRooms;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView classRoom;

        public ViewHolder(View view){
            super(view);
            classRoom = (TextView) view.findViewById(R.id.classroom_conetext);
        }
    }

    public ClassroomAdapter(List<String> classRooms){
        this.classRooms = classRooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classroom_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String classRoom = classRooms.get(position);
        holder.classRoom.setText(classRoom);
    }

    @Override
    public int getItemCount() {
        return classRooms.size();
    }
}
