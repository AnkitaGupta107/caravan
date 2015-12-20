package com.peppertap.caravan.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peppertap.caravan.R;
import com.peppertap.caravan.activities.CheckoutActivity;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SlotSelectionFragment extends DialogFragment {

    private static final String TAG = "SlotSelectionFragment";
    CheckoutActivity parentActivity;
    LayoutInflater fragmentInflater;

    private OnFragmentInteractionListener mListener;

    @InjectView(R.id.radio_gp_day)
    RadioGroup dayGp;
    @InjectView(R.id.cancel_btn)
    Button cancelBtn;
    @InjectView(R.id.set_btn)
    Button setBtn;

    private int selDay;

    private ArrayList<JsonObject> slotArray, showSlotsArray;

    private View view;

    private class DeliveryDay {
        private static final int TODAY = 0;
        private static final int TOMORROW = 1;
    }

    public SlotSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (CheckoutActivity)getActivity();
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checkout_schedule_slot_popup, null);
        fragmentInflater = inflater;
        ButterKnife.inject(this, view);
        slotArray = new ArrayList<JsonObject>();
        showSlotsArray = new ArrayList<JsonObject>();
        dayGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup gp, int id) {
//                Log.d(FragmentTags.slotSelectionTag, "in onCheckedChanged");
                int day = 0;
                if (id == R.id.today) {
                    day = DeliveryDay.TODAY;
                } else if (id == R.id.tomorrow) {

                    day = DeliveryDay.TOMORROW;
                }
            }

        });


        return view;
    }

    @OnClick(R.id.set_btn)
    public void setSlot() {
        dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void cancelSelection() {
        dismiss();
    }

    public void selectSlot(JsonObject selectedSlot) {
        if (selectedSlot == null) {
            mListener.showCustomViewToast("Please select a slot");
        }
        else {
            int selSlotId = selectedSlot.get("id").getAsInt();
            String day = selDay == DeliveryDay.TODAY ? "Today" : "Tomorrow";
            String slot = selectedSlot.get("start_time").getAsString() + " to " + selectedSlot.get("end_time").getAsString();

            dismiss();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void showCustomViewToast(String message);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

}
