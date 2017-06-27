package ness.edu.firebasedatabase.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ness.edu.firebasedatabase.R;
import ness.edu.firebasedatabase.models.User;

/**

 */
public class ShareFragment extends BottomSheetDialogFragment {

    //@BindString(R.string.app_name) String appName;

    @BindView(R.id.rvUsers)
    RecyclerView rvUsers;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        unbinder = ButterKnife.bind(this, view);
        //String appName = getResources().getString(R.string.app_name);

        Query ref = FirebaseDatabase.getInstance().getReference("Users");

        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(new UserAdapter(ref));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public static class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.UserViewHolder>{

        public UserAdapter(Query query) {
            super(User.class, R.layout.user_item, UserViewHolder.class, query);
        }

        @Override
        protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
            Context context = viewHolder.ivUserImage.getContext();
            viewHolder.tvUserName.setText(model.getDisplayName());
            Glide.with(context).load(model.getProfileImage()).into(viewHolder.ivUserImage);
        }

        public static class UserViewHolder extends RecyclerView.ViewHolder {
            CircularImageView ivUserImage;
            TextView tvUserName;

            public UserViewHolder(View itemView) {
                super(itemView);
                ivUserImage = (CircularImageView) itemView.findViewById(R.id.ivUserImage);
                tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            }
        }

    }

}
