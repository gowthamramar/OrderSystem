package com.techware.clickkart.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techware.clickkart.R;
import com.techware.clickkart.model.CountryBean;
import com.techware.clickkart.model.CountryListBean;


/**
 * Created by Jemsheer K D on 17 August, 2017.
 * Package in.techware.dearest.adapter
 * Project Dearest
 */

public class SelectDialerCodeRecyclerAdapter extends RecyclerView.Adapter<SelectDialerCodeRecyclerAdapter.ViewHolder> {

    private static final String TAG = "SelectDialerCodeRecyclerAdapter";
    private final Activity mContext;
    private SelectDialerCodeRecyclerAdapterListener selectDialerCodeRecyclerAdapterListener;
    private CountryListBean countryListBean;
    private boolean isLoadMore;
    private CountryBean selectedCountryBean;

    public SelectDialerCodeRecyclerAdapter(Activity mContext, CountryListBean countryListBean) {
        this.mContext = mContext;
        this.countryListBean = countryListBean;

    }

    public CountryListBean getCountryListBean() {
        return countryListBean;
    }

    public void setCountryListBean(CountryListBean countryListBean) {
        this.countryListBean = countryListBean;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_dialer_code, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutDialerCode(holder, position);

    }

    @Override
    public int getItemCount() {
        // dummy count.. to be changed.
        int count;
        try {
            count = countryListBean.getCountries().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutDialerCode(final ViewHolder holder, final int position) {
        final CountryBean bean = countryListBean.getCountries().get(position);
        holder.txtName.setText(bean.getName());
        holder.txtDialerCode.setText(bean.getDialCode());


        if(selectedCountryBean==bean){
            holder.ivSelected.setVisibility(View.VISIBLE);
        }else{
            holder.ivSelected.setVisibility(View.INVISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelected;
        TextView txtDialerCode;
        TextView txtName;

        ViewHolder(View lytItem) {
            super(lytItem);

            ivSelected = (ImageView) lytItem.findViewById(R.id.iv_item_dialer_code_select);
            txtName = (TextView) lytItem.findViewById(R.id.txt_item_dialer_code_country_name);
            txtDialerCode = (TextView) lytItem.findViewById(R.id.txt_item_dialer_code_dialer_code);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    CountryBean bean = countryListBean.getCountries().get(getLayoutPosition());
                    selectedCountryBean=bean;
                    notifyDataSetChanged();
                    selectDialerCodeRecyclerAdapterListener.onItemSelected(getLayoutPosition(), bean);
                }
            });
        }
    }

    public static interface SelectDialerCodeRecyclerAdapterListener {

        void onItemSelected(int position, CountryBean countryBean);

        void onSnackBarShow(String message);
    }

    public SelectDialerCodeRecyclerAdapterListener getSelectDialerCodeRecyclerAdapterListener() {
        return selectDialerCodeRecyclerAdapterListener;
    }


    public void setSelectDialerCodeRecyclerAdapterListener(SelectDialerCodeRecyclerAdapterListener selectDialerCodeRecyclerAdapterListener) {
        this.selectDialerCodeRecyclerAdapterListener = selectDialerCodeRecyclerAdapterListener;
    }
}
