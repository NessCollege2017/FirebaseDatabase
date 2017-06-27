package ness.edu.firebasedatabase;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ness.edu.firebasedatabase.models.ShoppingListProduct;
import ness.edu.firebasedatabase.models.ShoppingLists;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListItemsFragment extends DialogFragment {
    ShoppingLists shoppingList;
    @BindView(R.id.fabAddProduct)
    FloatingActionButton fabAddProduct;
    @BindView(R.id.etProductName)
    EditText etProductName;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list_items, container, false);
        unbinder = ButterKnife.bind(this, view);
        shoppingList = getArguments().getParcelable("list");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabAddProduct)
    public void onViewClicked() {
        //1) ref list items.
        DatabaseReference ref = FirebaseDatabase.
                                getInstance().
                                getReference("ListItems").
                                child(shoppingList.getListUID());

        //2) model
        String productName = etProductName.getText().toString();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Log.e("Ness", "No user");
            return;
        }

        ShoppingListProduct product = new ShoppingListProduct(productName,
                currentUser.getUid(),
                currentUser.getDisplayName(),
                false
                );


        ref.push().setValue(product);
    }
}
