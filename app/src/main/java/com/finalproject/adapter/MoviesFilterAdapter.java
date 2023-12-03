package com.finalproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.MoviesFilterRowBinding;
import com.finalproject.model.CategoryModel;
import com.finalproject.ui.owner.activity_home.fragments.FragmentOwnerMovies;
import com.finalproject.ui.user.activity_home.fragments.FragmentMovies;

import java.util.List;


public class MoviesFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private int currentPos = 1;
    private int oldPos = currentPos;
    private MyHolder oldHolder;

    public MoviesFilterAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public MoviesFilterAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MoviesFilterRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.movies_filter_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        if (oldHolder==null){
            oldHolder=myHolder;
            CategoryModel model=list.get(position);
            model.setSelected(true);
            list.set(position,model);
            myHolder.binding.setModel(model);
            oldPos=position;
           if (fragment instanceof FragmentMovies){
               FragmentMovies fragmentMovies =(FragmentMovies) fragment;
               fragmentMovies.setItemCategory(model,position);

           }
           if (fragment instanceof FragmentOwnerMovies){
               FragmentOwnerMovies fragmentOwnerMovies=(FragmentOwnerMovies) fragment;
               fragmentOwnerMovies.setItemCategory(model,position);

           }
        }
        myHolder.itemView.setOnClickListener(view -> {
            if (oldHolder!=null){
                CategoryModel oldCategoryModel=list.get(oldPos);
                oldCategoryModel.setSelected(false);
                list.set(oldPos,oldCategoryModel);

                MyHolder oHolder=(MyHolder) oldHolder;
                oHolder.binding.setModel(oldCategoryModel);
            }

            currentPos=myHolder.getAdapterPosition();
            CategoryModel categoryModel=list.get(currentPos);
            categoryModel.setSelected(true);
            list.set(currentPos,categoryModel);
            myHolder.binding.setModel(categoryModel);

            oldHolder=myHolder;
            oldPos=currentPos;

            if (fragment instanceof FragmentMovies){
                FragmentMovies fragmentMovies=(FragmentMovies) fragment;
                fragmentMovies.setItemCategory(categoryModel,currentPos);
            }


            if (fragment instanceof FragmentOwnerMovies){
                FragmentOwnerMovies fragmentOwnerMovies=(FragmentOwnerMovies) fragment;
                fragmentOwnerMovies.setItemCategory(categoryModel,currentPos);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MoviesFilterRowBinding binding;

        public MyHolder(MoviesFilterRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
