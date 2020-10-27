package com.cpen321.modernwaiter.ui.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpen321.modernwaiter.MainActivity;
import com.cpen321.modernwaiter.R;
import com.cpen321.modernwaiter.payment.MainPayment;
import com.cpen321.modernwaiter.ui.MenuItem;

import java.util.HashMap;

public class BillFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        Button startPaymentButton = view.findViewById(R.id.startPaymentButton);
        startPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainPayment.class);
                startActivity(intent);
            }
        });

        // Set the adapter
        Context context = view.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bill_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        MainActivity mainActivity = (MainActivity) getActivity();

        HashMap<MenuItem, Integer> billMap = mainActivity.tableSession.getBill();

        BillRecyclerAdapter billRecyclerAdapter = new BillRecyclerAdapter(billMap);
        recyclerView.setAdapter(billRecyclerAdapter);

        return view;
    }

}