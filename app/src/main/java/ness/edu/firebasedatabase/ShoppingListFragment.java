package ness.edu.firebasedatabase;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ness.edu.firebasedatabase.dialogs.AddListFragment;
import ness.edu.firebasedatabase.models.ShoppingLists;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {
    @BindView(R.id.fabAddList)
    FloatingActionButton fabAddList;
    @BindView(R.id.rvShoppingLists)
    RecyclerView rvShoppingLists;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        //1) query the data for the view
        //1.1) get the user.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return view; //no user -> no user Lists.

        //1.2) get the ref to the user-table
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("UserLists").child(user.getUid());
        //2) init a ShoppingListAdapter
        ShoppingListAdapter adapter = new ShoppingListAdapter(ref);

        //3) set The LayoutManager and adapter of the recyclerView
        rvShoppingLists.setAdapter(adapter);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabAddList)
    public void onFabAddListClicked() {
        AddListFragment dialog = new AddListFragment();
        dialog.show(getChildFragmentManager(), "AddListDialog");
    }


    public static class ShoppingListAdapter extends FirebaseRecyclerAdapter<ShoppingLists, ShoppingListAdapter.ShoppingListViewHolder>{
        public ShoppingListAdapter(Query query) {
            super(ShoppingLists.class,
                    R.layout.shopping_list_name_item,
                    ShoppingListViewHolder.class,
                    query
            );
        }

        @Override
        protected void populateViewHolder(ShoppingListViewHolder viewHolder, ShoppingLists model, int position) {
            viewHolder.tvListName.setText(model.getName());
        }

        public static class ShoppingListViewHolder extends RecyclerView.ViewHolder{
            TextView tvListName;
            FloatingActionButton fabListShare;

            public ShoppingListViewHolder(View itemView) {
                super(itemView);
                tvListName = (TextView) itemView.findViewById(R.id.tvListName);
                fabListShare = (FloatingActionButton) itemView.findViewById(R.id.fabListShare);
            }
        }
    }
}
