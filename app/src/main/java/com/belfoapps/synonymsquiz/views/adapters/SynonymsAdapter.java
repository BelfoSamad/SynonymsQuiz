package com.belfoapps.synonymsquiz.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belfoapps.synonymsquiz.R;
import com.belfoapps.synonymsquiz.pojo.Synonym;

import java.util.ArrayList;

public class SynonymsAdapter extends RecyclerView.Adapter<SynonymsAdapter.ViewHolder> {
    /*************************************** Declarations *****************************************/
    private ArrayList<Synonym> mSynonyms;

    /*************************************** Constructor ******************************************/
    public SynonymsAdapter(ArrayList<Synonym> mSynonyms) {
        this.mSynonyms = mSynonyms;
    }

    /*************************************** Methods **********************************************/
    @NonNull
    @Override
    public SynonymsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.synonyms_recyclerview_item, parent, false);

        return new SynonymsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SynonymsAdapter.ViewHolder holder, int position) {
        holder.word.setText(mSynonyms.get(position).getWord() + ":");
        holder.synonym.setText(mSynonyms.get(position).getSynonym());
    }

    @Override
    public int getItemCount() {
        if (mSynonyms == null) return 0;
        else return mSynonyms.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView word;
        TextView synonym;

        ViewHolder(View v) {
            super(v);
            word = v.findViewById(R.id.word_saved);
            synonym = v.findViewById(R.id.synonym_saved);
        }
    }
}
