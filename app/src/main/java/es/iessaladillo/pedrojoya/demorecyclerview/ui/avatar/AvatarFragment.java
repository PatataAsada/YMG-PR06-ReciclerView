package es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.AvatarFragmentBinding;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.profile.ProfileViewModel;

public class AvatarFragment extends Fragment {

    private AvatarViewModel avatarViewModel;
    private ProfileViewModel profileViewModel;
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
        avatarViewModel = ViewModelProviders.of(getActivity()).get(AvatarViewModel.class);
        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        initIntent();
    }

    private void initIntent() {

    }
}
