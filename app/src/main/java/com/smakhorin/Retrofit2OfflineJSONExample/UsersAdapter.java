package com.smakhorin.Retrofit2OfflineJSONExample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smakhorin.Retrofit2OfflineJSONExample.REST.DTO.UserData;
import com.smakhorin.Retrofit2OfflineJSONExample.Utils.NameComparator;

import java.util.Collections;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<UserData> userDataList;
    final private ListItemClickListener mOnClickListener;

    public UsersAdapter(List<UserData> userDataList,ListItemClickListener listener) {
        this.userDataList = userDataList;
        mOnClickListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(userDataList.get(position).getName());
        holder.email.setText(userDataList.get(position).getEmail());
    }

    public UserData getUser(int position) {
        return userDataList.get(position);
    }

    public void sortUsers(boolean reverse) {
        Collections.sort(userDataList, new NameComparator());
        if(reverse) {
            Collections.reverse(userDataList);
        }
        notifyDataSetChanged();
    }

    public void setUserData(List<UserData> userDataList) {
        this.userDataList = userDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_name);
            email = (TextView)itemView.findViewById(R.id.tv_email);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
