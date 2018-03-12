package example.com.quest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.quest.LoginActivity;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.R;

/**
 * Created by justinstanger on 3/4/18.
 */

public class CreateUserFragment extends android.support.v4.app.Fragment {
    Button mKnight, mWizard, mBoyElf, mGirlElf, mFairy, mCyclops, mViking, mVampire, mPrincess;
    EditText editText;
    TextView type;
    String username, usertype;
    ImageView avatar, avatarDrawer;
    FloatingActionButton submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_user, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit = view.findViewById(R.id.submit);
        editText = view.findViewById(R.id.create_user_name);
        type = view.findViewById(R.id.avatar_name);
        type.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        mKnight = view.findViewById(R.id.knight);
        mWizard = view.findViewById(R.id.wizard);
        mBoyElf = view.findViewById(R.id.boy_elf);
        mGirlElf = view.findViewById(R.id.girl_elf);
        mFairy = view.findViewById(R.id.fairy);
        mCyclops = view.findViewById(R.id.cyclops);
        mViking = view.findViewById(R.id.viking);
        mVampire = view.findViewById(R.id.vampire);
        mPrincess = view.findViewById(R.id.princess);


        avatar = view.findViewById(R.id.player_avatar);
        submit.setVisibility(View.INVISIBLE);

        mKnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_knight));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Knight");
                usertype = "knight";

            }
        });
        mWizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_wizard));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Wizard");
                usertype = "wizard";


            }
        });
        mBoyElf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_elf));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Elf Boy");
                usertype = "elf boy";


            }
        });
        mGirlElf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_elf));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Elf Girl");
                usertype = "elf girl";


            }
        });
        mFairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_fairy));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Fairy");
                usertype = "fairy";


            }
        });
        mCyclops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_cyclops));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Cyclops");
                usertype = "cyclops";

            }
        });
        mVampire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_vampire));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Vampire");
                usertype = "vampire";

            }
        });
        mPrincess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_princess));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Princess");
                usertype = "princess";

            }
        });
        mViking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);

                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_viking));
                editText.setVisibility(View.VISIBLE);
                type.setVisibility(View.VISIBLE);
                type.setText("Viking");
                usertype = "viking";

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText.getText().toString();
                usertype = type.getText().toString();
                if (!username.equals("null")) {
                    if (usertype.length() > 1) {
                        if (type.getText().length() > 0) {

                            UserProfileObject userProfileObject = new UserProfileObject(
                                    username, "password", usertype);
                            ((LoginActivity)getActivity()).saveUser(userProfileObject);

                        }
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

