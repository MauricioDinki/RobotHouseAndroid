package com.mauriciodinki.robothouse.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciodinki.robothouse.R;
import com.mauriciodinki.robothouse.domain.Movement;

import java.util.ArrayList;

/**
 * Created by mauriciodinki on 29/12/15.
 */
public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movement> movements;

    public MovementAdapter(Context context) {
        this.context = context;
        this.movements = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movement_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movement movement = movements.get(position);
        holder.movementDate.setText(movement.getDate());
        holder.movementTime.setText(movement.getTime());
    }

    @Override
    public int getItemCount() {
        return movements.size();
    }

    public void addAll(@NonNull ArrayList<Movement> movements){
        if (movements == null)
            throw new NullPointerException("The items cannot be null");

        this.movements.addAll(movements);
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<Movement> movements) {
        this.movements = movements;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView movementDate;
        public TextView movementTime;

        public ViewHolder(View itemView) {
            super(itemView);

            movementDate = (TextView) itemView.findViewById(R.id.movement_card_date);
            movementTime = (TextView) itemView.findViewById(R.id.movement_card_time);
        }
    }
}
