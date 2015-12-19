package com.peppertap.caravan.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.fragments.ShopFragment;
import com.peppertap.caravan.network.Urls;
import com.peppertap.caravan.network.volley.MyJsonObjectRequest;
import com.peppertap.caravan.network.volley.ResponseListener;
import com.peppertap.caravan.utils.JsonHelper;
import com.peppertap.caravan.utils.RetryCallback;
import com.peppertap.caravan.viewHolders.ProductHolder;
import com.peppertap.caravan.viewHolders.SearchBarHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by samvedana on 19/12/15.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter {
    private static String searchRequestTAG = "TAG";

    private static final int ITEM_TYPE_SEARCH = 0;
    private static final int ITEM_TYPE_PRODUCT = 1;

    CaravanApp globalApplication;
    ShopFragment mFragment;

    private List<ProductHelper.ProductGroup> data = new ArrayList<>();

    public SearchResultsAdapter(CaravanApp app, ShopFragment fragment) {
        globalApplication = app;
        mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SEARCH:
                final View searchItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_bar, parent, false);

                final MaterialEditText search = (MaterialEditText) searchItem.findViewById(R.id.search);

                TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String query = search.getText().toString();
                            if(query != null && !query.isEmpty()){
                                performSearch(query);
                                //hideKeyboard(search_text_view);
                            }
                            handled = true;
                        }
                        return handled;
                    }
                };

                TextWatcher mTextWatcher = new TextWatcher() {
                    private Timer timer=new Timer();
                    private final long DELAY = 500; // in ms

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String query = null;
                        if (s != null) {
                            query = s.toString();
                        }
                        final String query1 = query;

                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                mFragment.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        performSearch(query1);
                                    }
                                });
                            }

                        }, DELAY);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };

                SearchBarHolder searchBarHolder = new SearchBarHolder(searchItem, mTextWatcher, editorActionListener);
                return searchBarHolder;
            case ITEM_TYPE_PRODUCT:
                View listItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shop_line_item, parent, false);
                ProductHolder productHolder = new ProductHolder(listItemView, globalApplication);
                return productHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_TYPE_SEARCH:
                break;
            case ITEM_TYPE_PRODUCT:
                ((ProductHolder) holder).bindData(data.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_SEARCH;
        }
        return ITEM_TYPE_PRODUCT;
    }

    public void performSearch(final String query) {
        cancelSearchRequest();
        String zone_id = globalApplication.getAppData().getZone_id();
        String search_url = Urls.getSearchUrl(query, globalApplication.getAppData().getSession_id(), zone_id);

        mFragment.reset();
        mFragment.showProgressDialog();

        ResponseListener<JsonObject> responseListener = new ResponseListener<JsonObject>() {
            @Override
            public void onResponse(JsonObject result) {
                Timber.d(result.toString());

                if (JsonHelper.getValueOrNone(result, "status").equals("OK")) {
                    ProductHelper productHelper = ProductHelper.fromJson(result);

                    if (productHelper.getTitleArray().size() == 0) {
                        mFragment.hideProgressDialog();
                        mFragment.showEmptyView("No products found", "Try searching with a different keyword or for alternatives");
                    } else {
                        mFragment.hideProgressDialog();
                        showSearchResults(productHelper.getAllProductGroups(), query);
                    }
                } else {
                    Timber.e("Server error after searching products");
                    Timber.e(result.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.e("something went wrong while fetching products " + error.getMessage());
                boolean bypassDialog = false;
                boolean nwError = false;
                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    mFragment.showNetworkErrorView(new RetryCallback() {
                        @Override
                        public void onRetry() {
                            performSearch(query);
                        }
                    });
                    nwError = true;
                } else {
                    mFragment.showAlertDialog("Error", "Oops! Something went wrong please try again");
                }
                mFragment.hideProgressDialog();
                mFragment.showEmptyView("No products found", "Try searching with a different keyword or for alternatives");
            }
        };

        MyJsonObjectRequest request = new MyJsonObjectRequest(mFragment, search_url, responseListener);
        request.setTag(searchRequestTAG);

        globalApplication.getRequestQueue().add(request);
    }

    public void cancelSearchRequest(){
        globalApplication.getRequestQueue().cancelAll(searchRequestTAG);
    }

    public void showSearchResults(ArrayList<ProductHelper.ProductGroup> pList, String query) {
        //todo populate data
        data.clear();
        data.addAll(pList);
        notifyDataSetChanged();
    }
}
