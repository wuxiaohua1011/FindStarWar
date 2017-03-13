package example.com.findstarwar;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by micha on 3/12/2017.
 */

public class CharacterFragment extends ListFragment{
    private List<StarWarCharacter> characters = MainActivity.characters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        CharacterAdapter adapter = new CharacterAdapter(getActivity(),characters);
        setListAdapter(adapter);
        return rootView;
    }
}
