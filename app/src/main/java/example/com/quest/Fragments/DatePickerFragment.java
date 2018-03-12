package example.com.quest.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.R;
import example.com.quest.RecyclerViewHelpers.Adapters.MainQuestViewAdapter;
import example.com.quest.Singleton.Singleton;

/**
 * Created by justinstanger on 3/6/18.
 */

public class DatePickerFragment extends DialogFragment {

    private DatePicker datePicker;
    public MainQuestObject mainQuestObject;

    public interface DateDialogListener {
        void onFinishDialog(Date date, MainQuestObject main);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.calendar_dialog,null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Set Date For Quest")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year,mon,day).getTime();
                                DateDialogListener activity = (DateDialogListener) Singleton.getInstance().getAdapter();
                                //DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                //String reportDate = df.format(date);
                                //Singleton.getInstance().setSetDate(reportDate);

                                //Toast.makeText(Singleton.getInstance().getContext(), reportDate, Toast.LENGTH_SHORT).show();
                                activity.onFinishDialog(date, mainQuestObject);
                                dismiss();
                            }
                        })
                .create();
    }

    public interface OnFragmentInteractionListener {
    }
}