package com.vruzeda.wanikani.fragment;

import com.vruzeda.wanikani.R;
import com.vruzeda.wanikani.api.WanikaniApiProvider;
import com.vruzeda.wanikani.api.model.BaseResponse;
import com.vruzeda.wanikani.api.model.Kana;

import java.util.List;

import retrofit2.Call;

public class RadicalListFragment extends KanaListFragment {

    @Override
    protected int getTitleResourceId() {
        return R.string.fragment_radical_list_title;
    }

    @Override
    protected Call<BaseResponse<List<Kana>>> getKanaList(String apiToken) {
        return WanikaniApiProvider.getInstance().getRadicalList(apiToken);
    }

    @Override
    protected int getCardBackgroundColorId() {
        return R.color.colorRadical;
    }
}
