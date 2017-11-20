package kr.co.within.hiroworld.ui;

import android.support.v4.app.Fragment;

import kr.co.within.hiroworld.dagger.HasComponent;

public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
