package com.colin.livetvtab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {

    private View layout;
    private TextView tv;
    int i;

    public static MyFragment getMyFragment(int i) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", i);
        myFragment.setArguments(bundle);
        return myFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment,
                container, false);
        tv = (TextView) layout.findViewById(R.id.tv_fragment);
        tv.setText("第" + getArguments().getInt("index") + "个");
        return layout;
    }

}
