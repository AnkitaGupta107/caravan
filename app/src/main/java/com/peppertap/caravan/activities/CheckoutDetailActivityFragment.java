package com.peppertap.caravan.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peppertap.caravan.R;
import com.peppertap.caravan.fragments.SlotSelectionFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutDetailActivityFragment extends Fragment implements View.OnClickListener{

    @InjectView(R.id.asap)
    LinearLayout asapLayout;
    @InjectView(R.id.slot)
    LinearLayout slotLayout;
    @InjectView(R.id.proceed_btn) Button proceedBtn;
    @InjectView(R.id.slot_name) TextView slotName;
    @InjectView(R.id.asap_title) TextView asapTitle;

    private int selDeliveryTimeLayout  = -1;

    private class TabType {
        private static final int DELIVERY_TIME = 0;
    }

    private class DeliveryTime {
        private static final int NOW = 0;
        private static final int LATER = 1;
    }

    public CheckoutDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkout_detail, container, false);
        ButterKnife.inject(this, rootView);

        asapLayout.setOnClickListener(this);
        slotLayout.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.asap:
                setDeliveryTime(DeliveryTime.NOW);
                break;
            case R.id.slot:
                setDeliveryTime(DeliveryTime.LATER);
                break;
            case R.id.proceed_btn:
                Intent intent = new Intent(getActivity(), CheckoutConfirmActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void slotsAvailable() {
        int blackColor = getResources().getColor(R.color.black);
        int lightBlackColor = getResources().getColor(R.color.orderDetail_paymentsTitle);
        slotName.setTextColor(blackColor);
        slotName.setText("Repeat");
        slotLayout.setBackgroundDrawable(null);
        slotLayout.setOnClickListener(this);
    }

    private void switchOptionUiAspect(int tabType, int selection) {
        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);

        Drawable leftSelected = getActivity().getResources().getDrawable(R.drawable.checkout_left_active_btn);
        Drawable rightSelected = getActivity().getResources().getDrawable(R.drawable.checkout_right_active_btn);

        switch (tabType) {
            case TabType.DELIVERY_TIME:
                if (selection == DeliveryTime.NOW) {
                    asapTitle.setTextColor(whiteColor);
                    asapLayout.setBackgroundDrawable(leftSelected);
                    slotsAvailable();
                    slotsAvailable();
                } else {
                    asapTitle.setTextColor(blackColor);
                    asapLayout.setBackgroundDrawable(null);
                    slotName.setTextColor(whiteColor);
                    slotLayout.setBackgroundDrawable(rightSelected);
                }
                break;
        }
    }

    private void setDeliveryTime(int deliveryTime) {
        switch (deliveryTime) {
            case DeliveryTime.NOW:
                switchOptionUiAspect(TabType.DELIVERY_TIME, DeliveryTime.NOW);
                    selDeliveryTimeLayout = DeliveryTime.NOW;
                break;
            case DeliveryTime.LATER:
                //get delivery slots to show from server

//                DialogFragment fr = new SlotSelectionFragment();
//                fr.show(getActivity().getFragmentManager(), FragmentTags.slotSelectionTag);
                break;
        }
    }
}
