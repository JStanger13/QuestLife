package example.com.quest.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import example.com.quest.R;

/**
 * Created by justinstanger on 3/9/18.
 */

public class LoginFragment extends Fragment {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private TextView mStatusTextView;

    public static MainQuestFragment newInstance() {
        MainQuestFragment mainQuestFragmentNewInstance = new MainQuestFragment();
        return mainQuestFragmentNewInstance;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStatusTextView = view.findViewById(R.id.status);


    }
}
