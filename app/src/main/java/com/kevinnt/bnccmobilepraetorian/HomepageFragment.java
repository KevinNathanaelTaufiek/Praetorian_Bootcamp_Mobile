package com.kevinnt.bnccmobilepraetorian;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomepageFragment extends Fragment {

    private ImageView iv_picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);

        iv_picture = v.findViewById(R.id.iv_picture);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://bnccmobilepraetorian-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference ref = db.getReference("gambar");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(getContext())
                        .load(snapshot.getValue())
                        .into(iv_picture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fl_fragment_container, new DetailFragment())
                        .addToBackStack("")
                        .commit();
            }
        });

        return v;
    }
}