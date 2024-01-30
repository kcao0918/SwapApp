package com.example.swapapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.swapapp.InfoActivity;
import com.example.swapapp.SwapActivity;
import com.example.swapapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] test = {"test",
        "test2",
        "test3"};
        final ListView swapList = binding.swapList;
        ArrayAdapter<String> swapViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, test); // add firebase list

        swapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openSwapActivity();
            }
        });
        swapList.setAdapter(swapViewAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void openSwapActivity() {
        Intent i = new Intent(getActivity(), SwapActivity.class);
        startActivity(i);
    }
}