package com.vruzeda.wanikani.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vruzeda.wanikani.R;
import com.vruzeda.wanikani.activity.MainActivity;
import com.vruzeda.wanikani.api.model.BaseResponse;
import com.vruzeda.wanikani.api.model.Kana;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class KanaListFragment extends Fragment {

    abstract protected int getTitleResourceId();

    abstract protected Call<BaseResponse<List<Kana>>> getKanaList(String apiToken);

    abstract protected int getCardBackgroundColorId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_radical_list, container, false);

        String apiToken = getArguments().getString(MainActivity.MAIN_ACTIVITY_API_TOKEN);
        getKanaList(apiToken).enqueue(new Callback<BaseResponse<List<Kana>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Kana>>> call, Response<BaseResponse<List<Kana>>> response) {
                if (response.isSuccessful()) {
                    List<Kana> kanaList = response.body().getRequestedInformation();

                    RecyclerView radicalListView = view.findViewById(R.id.activity_main_radical_list);
                    radicalListView.setAdapter(new KanaListAdapter(kanaList));
                    radicalListView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Kana>>> call, Throwable t) {
                // Do nothing
            }
        });

        return view;
    }

    public String getTitle(Context context) {
        return context.getString(getTitleResourceId());
    }

    private class KanaListAdapter extends RecyclerView.Adapter<KanaListViewHolder> {

        List<Kana> kanaList;

        KanaListAdapter(List<Kana> kanaList) {
            this.kanaList = kanaList;
        }

        @Override
        public KanaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_kana, parent, false);
            cardView.setCardBackgroundColor(getResources().getColor(getCardBackgroundColorId()));
            return new KanaListViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(KanaListViewHolder holder, int position) {
            holder.configureKana(kanaList.get(position));
        }

        @Override
        public int getItemCount() {
            return kanaList.size();
        }
    }

    private class KanaListViewHolder extends RecyclerView.ViewHolder {

        private TextView character;
        private ImageView image;
        private TextView meaning;

        KanaListViewHolder(View itemView) {
            super(itemView);
            character = itemView.findViewById(R.id.card_kana_character);
            image = itemView.findViewById(R.id.card_kana_image);
            meaning = itemView.findViewById(R.id.card_kana_meaning);
        }

        void configureKana(Kana kana) {
            if (kana.getCharacter() != null) {
                character.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                character.setText(kana.getCharacter());
            } else {
                character.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                Picasso.with(getContext())
                        .load(kana.getImage())
                        .into(image);
            }
            meaning.setText(kana.getMeaning());
        }
    }
}
