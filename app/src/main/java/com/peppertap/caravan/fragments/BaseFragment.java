package com.peppertap.caravan.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.activities.MinimalActivity;
import com.peppertap.caravan.utils.RetryCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * Created by samvedana on 05/11/15.
 */
public abstract class BaseFragment extends Fragment {
    protected CaravanApp globalApplication;

    protected static final int NONE = 0;

    protected int fragment_layout_res_id = NONE;

    View baseView;
    @InjectView(R.id.no_internet_view) View noInternetView;
    @InjectView(R.id.empty_view) View emptyView;
    @InjectView(R.id.title)
    TextView emptyMainTitle;
    @InjectView(R.id.sub_title) TextView emptySubTitle;
    @InjectView(R.id.retry_button) ImageButton tryAgainButton;
    @InjectView(R.id.fragment_content) View mContentView;
    @InjectView(R.id.loader_view) View loaderView;
    @InjectView(R.id.loader) ProgressBar loader;

    RetryCallback retryCallback = null;

    private int mShortAnimationDuration = 500;

    private enum PossibleViews {
        CONTENT_VIEW, NO_INTERNET_VIEW, EMPTY_VIEW
    }

    private PossibleViews currentView;

    private boolean loaderVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        globalApplication = (CaravanApp) getActivity().getApplication();
        setUpContentView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_base, container, false);

        if (fragment_layout_res_id != NONE) {
            FrameLayout fragmentContainer = (FrameLayout) baseView.findViewById(R.id.fragment_content);
            inflater.inflate(fragment_layout_res_id, fragmentContainer, true);
        }

        ButterKnife.inject(this, baseView);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("tryAgainButton clicked");
                if (retryCallback != null) {
                    reset();
                    retryCallback.onRetry();
                }
            }
        });

        configureLoader();

        mContentView.setVisibility(View.VISIBLE);
        noInternetView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        currentView = PossibleViews.CONTENT_VIEW;

        return baseView;
    }

    protected abstract void setUpContentView();

    protected boolean useNoInternetView() {
        return true;
    }

    protected boolean useEmptyView() {
        return true;
    }

    protected boolean useLoaderView() {
        return true;
    }

    private void crossFadeUi(final View current, View next, int animDuration) {
        /*
        current & next can only be one of mContentView, emptyView or noInternetView
         */
        Timber.d("crossFadeUi");
        next.setAlpha(0f);
        next.setVisibility(View.VISIBLE);

        next.animate()
                .alpha(1f)
                .setDuration(animDuration)
                .setListener(null);
        if (current.getVisibility() == View.VISIBLE) {
            current.animate()
                    .alpha(0f)
                    .setDuration(animDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            current.setVisibility(View.GONE);
                            current.setAlpha(1f);
                        }
                    });
        }
        else {
            Timber.e("crossFadeUi - current not visible");
        }
    }

    public void showNetworkErrorView(RetryCallback callback){
        if (useNoInternetView()) {
            retryCallback = callback;
            if (currentView == PossibleViews.CONTENT_VIEW) {
                Timber.d("showNetworkErrorView : Content_View -> No_Internet_View");
                crossFadeUi(mContentView, noInternetView, mShortAnimationDuration);
                currentView = PossibleViews.NO_INTERNET_VIEW;
            }
            else if (currentView == PossibleViews.EMPTY_VIEW) {
                Timber.d("showNetworkErrorView : Empty_View -> No_Internet_View");
                crossFadeUi(emptyView, noInternetView, mShortAnimationDuration);
                currentView = PossibleViews.NO_INTERNET_VIEW;
            }
            else {
                Timber.d("showNetworkErrorView : At No_Internet_View");
            }
        }
        else {
            Timber.e("No Internet View not allowed");
        }
    }

    public void showEmptyView(String title, String msg) {
        if (useEmptyView()) {
            emptyMainTitle.setText(title);
            emptySubTitle.setText(msg);
            if (currentView == PossibleViews.CONTENT_VIEW) {
                Timber.d("showEmptyView : Content_View -> Empty_View");
                crossFadeUi(mContentView, emptyView, mShortAnimationDuration);
                currentView = PossibleViews.EMPTY_VIEW;
            }
            else if (currentView == PossibleViews.NO_INTERNET_VIEW) {
                Timber.d("showEmptyView : No_Internet_View -> Empty_View");
                crossFadeUi(noInternetView, emptyView, mShortAnimationDuration);
                currentView = PossibleViews.EMPTY_VIEW;
            }
            else {
                Timber.d("showEmptyView : At Empty_View");
            }
        }
        else {
            Timber.e("Empty View not allowed");
        }
    }

    public void reset() {
        Timber.d("reset");
        mContentView.setVisibility(View.VISIBLE);
        noInternetView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        currentView = PossibleViews.CONTENT_VIEW;
    }

    public void showProgressDialog() {
        if (useLoaderView()) {
            if (currentView == PossibleViews.CONTENT_VIEW) {
                Timber.d("showProgressDialog : Content_View -> Loader_View");
                mContentView.setVisibility(View.GONE);
            }
            else if (currentView == PossibleViews.EMPTY_VIEW) {
                Timber.d("showProgressDialog : Empty_View -> Loader_View");
                emptyView.setVisibility(View.GONE);
            }
            else {
                Timber.d("showProgressDialog : No_Internet_View -> Loader_View");
                noInternetView.setVisibility(View.GONE);
            }
            loaderVisible = true;
            loaderView.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressDialog() {
        if (useLoaderView() && loaderVisible) {
            loaderVisible = false;
            loaderView.setVisibility(View.GONE);
            if (currentView == PossibleViews.CONTENT_VIEW) {
                Timber.d("hideProgressDialog : Loader_View -> Content_View");
                mContentView.setVisibility(View.VISIBLE);
            } else if (currentView == PossibleViews.EMPTY_VIEW) {
                Timber.d("hideProgressDialog : Loader_View -> Empty_View");
                emptyView.setVisibility(View.VISIBLE);
            } else {
                Timber.d("hideProgressDialog : Loader_View -> No_Internet_View");
                noInternetView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void configureLoader() {

    }

    public void showAlertDialog(String title, String message) {
        ((MinimalActivity) getActivity()).showAlertDialog(title, message);
    }

}
