package com.techware.clickkart.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.techware.clickkart.R;
import com.techware.clickkart.adapter.SelectDialerCodeRecyclerAdapter;
import com.techware.clickkart.config.TypefaceCache;
import com.techware.clickkart.model.CountryBean;
import com.techware.clickkart.model.CountryListBean;
import com.techware.clickkart.util.AppConstants;

/**
 * Created by Jemsheer K D on 17 August, 2017.
 * Package in.techware.dearest.dialogs
 * Project Dearest
 */

public class SelectDialerCodeDialog {

    private final Activity mContext;
    private Typeface typeface;
    private CountryListBean countryListBean;
    private SelectDialerCodeDialogActionListener selectDialerCodeDialogActionListener;
    private Dialog dialogSelectDialerCode;
    private RecyclerView rvSelectDialerCode;
    private LinearLayoutManager linearLayoutManager;
    private SelectDialerCodeRecyclerAdapter adapter;
    private EditText etxtSearch;

    public SelectDialerCodeDialog(Activity mContext) {
        this.mContext = mContext;

        this.countryListBean = AppConstants.getCountryListBean();

        try {
            typeface = TypefaceCache.getInstance().getTypeface(mContext.getApplicationContext(), "Roboto-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSelectDialerCodeDialog();
    }

    public void show() {
        dialogSelectDialerCode.show();
    }

    private void setSelectDialerCodeDialog() {

        dialogSelectDialerCode = new Dialog(mContext, R.style.ThemeDialogCustom_NoBackground);
        dialogSelectDialerCode.setContentView(R.layout.dialog_select_dialer_code);
        dialogSelectDialerCode.setTitle(R.string.title_select_dialer_code);


        etxtSearch = (EditText) dialogSelectDialerCode.findViewById(R.id.etxt_select_dialer_code_search);
        etxtSearch.setTypeface(typeface);

        rvSelectDialerCode = (RecyclerView) dialogSelectDialerCode.findViewById(R.id.rv_select_dialer_code);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSelectDialerCode.setLayoutManager(linearLayoutManager);

        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String query = etxtSearch.getText().toString();
                if (query.length() > 0)
                    countryListBean.setCountries(AppConstants.getCountryListBean().search(query));
                else
                    countryListBean = AppConstants.getCountryListBean();

                populateSelectDialerCode();
            }
        });

        populateSelectDialerCode();

    }

    private void populateSelectDialerCode() {

        if (adapter == null) {
            adapter = new SelectDialerCodeRecyclerAdapter(mContext, countryListBean);
            adapter.setSelectDialerCodeRecyclerAdapterListener(new SelectDialerCodeRecyclerAdapter.SelectDialerCodeRecyclerAdapterListener() {

                @Override
                public void onItemSelected(int position, CountryBean countryBean) {
                    selectDialerCodeDialogActionListener.onItemSelected(position, countryBean);
                    dialogSelectDialerCode.dismiss();
                }

                @Override
                public void onSnackBarShow(String message) {
                    selectDialerCodeDialogActionListener.onSnackBarShow(message);
                }
            });
            rvSelectDialerCode.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setCountryListBean(countryListBean);
            adapter.notifyDataSetChanged();
        }

    }

    public interface SelectDialerCodeDialogActionListener {

        void onItemSelected(int position, CountryBean countryBean);

        void onSnackBarShow(String message);

        void onSwipeRefreshChange(boolean isSwipeRefreshing);
    }

    public SelectDialerCodeDialogActionListener getSelectDialerCodeDialogActionListener() {
        return selectDialerCodeDialogActionListener;
    }


    public void setSelectDialerCodeDialogActionListener(SelectDialerCodeDialogActionListener selectDialerCodeDialogActionListener) {
        this.selectDialerCodeDialogActionListener = selectDialerCodeDialogActionListener;
    }
}
