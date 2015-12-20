package com.peppertap.caravan.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.peppertap.caravan.R;
import com.peppertap.caravan.activities.CheckoutDetailActivity;
import com.peppertap.caravan.events.UIEvents;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class SlotSelectionFragment extends DialogFragment {

    private static final String TAG = "SlotSelectionFragment";
    CheckoutDetailActivity parentActivity;
    LayoutInflater fragmentInflater;

    private OnFragmentInteractionListener mListener;

    @InjectView(R.id.radio_gp_day)
    RadioGroup dayGp;
    @InjectView(R.id.cancel_btn)
    Button cancelBtn;
    @InjectView(R.id.set_btn)
    Button setBtn;

    private RepetitionFrequency frequency = RepetitionFrequency.NONE;

    private ArrayList<JsonObject> slotArray, showSlotsArray;

    private View view;

    public enum RepetitionFrequency {
        DAILY, WEEKLY, NONE
    }

    public SlotSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (CheckoutDetailActivity) getActivity();
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
                if (id == R.id.today) {
                    frequency = RepetitionFrequency.DAILY;
                } else if (id == R.id.weekly) {
                    frequency = RepetitionFrequency.WEEKLY;
                }
            }

        });


        return view;
    }

    @OnClick(R.id.set_btn)
    public void setSlot() {
        if (frequency == RepetitionFrequency.NONE) {
            //mListener.showCustomViewToast("Please select a slot");
            //parentActivity.hideProgressDialog();
        }
        else {
            UIEvents.OrderFrequencyChosen myEvent =
                    new UIEvents.OrderFrequencyChosen(frequency);
            EventBus.getDefault().post(myEvent);

            //parentActivity.hideProgressDialog();
            dismiss();
        }
        dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void cancelSelection() {
        dismiss();
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
