package cl.telematica.patricio.certamen2_v2.presenters;

/**
 * Created by Patricio on 30-09-2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.telematica.patricio.certamen2_v2.R;
import cl.telematica.patricio.certamen2_v2.presenters.modelo.repos;

public class UIAdapter extends RecyclerView.Adapter<UIAdapter.ViewHolder> {
    private List<repos> mDataset;
    private ItemClickListener clickListener;

    public UIAdapter(List<repos> myDataset) {
        mDataset = myDataset;
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public UIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_repo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        repos repo = mDataset.get(position);

        holder.mTextView.setText(repo.getNombre());
        holder.mDescriptionView.setText(repo.getDescription());
        holder.mActualView.setText(repo.getActual());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;
        public TextView mDescriptionView;
        public TextView mActualView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textName);
            mActualView = (TextView) v.findViewById(R.id.textActual);
            mDescriptionView = (TextView) v.findViewById(R.id.textDescription);
            itemView.setOnClickListener(this); // bind the listener
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}