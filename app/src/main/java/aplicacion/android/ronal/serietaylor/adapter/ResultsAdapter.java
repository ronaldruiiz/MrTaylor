package aplicacion.android.ronal.serietaylor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import aplicacion.android.ronal.serietaylor.R;
import aplicacion.android.ronal.serietaylor.data.*;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private List<Results> mResults;
    private int context;
    OnItemClickListener onItemClickListener;


    public ResultsAdapter(List<Results> mResults, int context, OnItemClickListener onItemClickListener) {
        this.mResults = mResults;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(context,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind((Results) mResults.get(position),onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView)itemView.findViewById(R.id.txtTitulo);
            imageView =(ImageView)itemView.findViewById(R.id.imgResult);

        }
        public void bind(final Results results, final OnItemClickListener onItemClickListener) {

            textView.setText(results.getmTitulo());

            Picasso.get().load(results.getUrl()).resize(800,600).onlyScaleDown().centerInside().into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(results,getAdapterPosition());
                }
            });

        }

    }


    public interface OnItemClickListener {
        void OnItemClick(Results results,int position);
    }
}
