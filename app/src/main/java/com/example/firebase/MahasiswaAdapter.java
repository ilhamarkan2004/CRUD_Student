package com.example.firebase;

import android.app.Activity;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {
    private Activity context;
    private ArrayList<Mahasiswa> localDataSet = new ArrayList<>();
    private DAOMahasiswa DAOMahasiswa;

    public MahasiswaAdapter(Activity context, DAOMahasiswa DAOMahasiswa) {
        this.context = context;
        this.DAOMahasiswa = DAOMahasiswa;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cardMahasiswa = layoutInflater.inflate(R.layout.card_mahasiswa,
                parent, false);

        return new MahasiswaViewHolder(cardMahasiswa, context, DAOMahasiswa);
    }

    public void setData (ArrayList<Mahasiswa> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @Override
    public void onBindViewHolder(
            @NonNull MahasiswaViewHolder holder,
            int position
    ) {
        holder.bindView(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        private Activity context;
        private Button updateButton, deleteButton;
        private TextView card_nama, card_nim;
        public DAOMahasiswa DAOMahasiswa;

        public MahasiswaViewHolder(
                View view,
                Activity context,
                DAOMahasiswa DAOMahasiswa
        ) {
            super(view);
            this.context = context;
            this.DAOMahasiswa = DAOMahasiswa;
            initComponent(view);
        }

        private void initComponent(View view) {
            card_nama = view.findViewById(R.id.card_nama);
            card_nim = view.findViewById(R.id.card_nim);
            updateButton = view.findViewById(R.id.updateButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }

        public void bindView(Mahasiswa mahasiswa) {
            card_nama.setText(mahasiswa.getNama());
            card_nim.setText(mahasiswa.getNim());
            updateButton.setOnClickListener(view -> updateMahasiswa(mahasiswa));
            deleteButton.setOnClickListener(view -> deleteMahasiswa(mahasiswa));
        }

        private void updateMahasiswa(Mahasiswa mahasiswa) {
            Intent intent = new Intent(context, UpdateMahasiswaActivity.class);
            intent.putExtra("KEY", mahasiswa.getKey());
            context.startActivity(intent);
        }

        private void deleteMahasiswa(Mahasiswa mahasiswa) {
            DAOMahasiswa.delete(mahasiswa.getKey()).addOnSuccessListener(res ->
                    Toast.makeText(
                            context,
                            "Delete berhasil",
                            Toast.LENGTH_LONG
                    ).show()).addOnFailureListener(error -> Toast.makeText(
                    context,
                    "Delete gagal: "+ error.getLocalizedMessage(),
                    Toast.LENGTH_LONG

            ).show());
        }
    }

}

