package com.example.wordbuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.MyViewHolder> {

    List<Translation> translations;
    Context context;

    public recycleViewAdapter(List<Translation> translations, Context context) {
        this.translations = translations;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_word, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_foreignWord.setText(translations.get(position).getForeignWord());
        holder.tv_targetLanguageTranslation.setText(translations.get(position).getTargetLanguageTranslation());
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foreignWord;
        TextView tv_targetLanguageTranslation;
        ImageView iv_mastery;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foreignWord = itemView.findViewById(R.id.tv_foreignWord);
            tv_targetLanguageTranslation = itemView.findViewById(R.id.tv_targetLangTranslations);
            iv_mastery = itemView.findViewById(R.id.iv_mastery);
        }
    }
}
