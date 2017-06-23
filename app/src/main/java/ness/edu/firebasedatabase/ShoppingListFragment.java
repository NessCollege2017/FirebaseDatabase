package ness.edu.firebasedatabase;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabAddList)
    public void onFabAddListClicked() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //User Shopping Lists
        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("UserShoppingLists").
                child(user.getUid());
        //TODO: Add a dialog for adding user lists
        ref.push().setValue(new ShoppingLists());
    }
}
