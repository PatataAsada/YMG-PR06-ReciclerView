package es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.AvatarFragmentBinding;

public class AvatarFragment extends Fragment {

    private AvatarViewModel avatarViewModel;
    private AvatarFragmentBinding avatarFragmentBinding;
    private Intent extra_avatar;

    public static AvatarFragment newInstance() {
        return new AvatarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.avatar_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatarFragmentBinding = DataBindingUtil.setContentView(getActivity(),R.layout.avatar_fragment);
        avatarViewModel = ViewModelProviders.of(this).get(AvatarViewModel.class);
        initIntent();
        initViews();
        initListeners();
    }

    private void initIntent(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            extra_avatar = getIntent();
            avatar = extra_avatar.getParcelableExtra("EXTRA_AVATAR");
            selectedAvatar((int) avatar.getId());
        }else{
            chosenAvatar = savedInstanceState.getInt("CHOSENAVATAR",0);
            selectedAvatar(chosenAvatar);
        }
    }
}
