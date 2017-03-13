package example.com.findstarwar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by micha on 3/12/2017.
 */

public class CharacterAdapter extends ArrayAdapter<StarWarCharacter> {
    public CharacterAdapter(Context context, List<StarWarCharacter> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.character_adapter,null);
        }
        StarWarCharacter character = getItem(position);
        TextView textViewName = (TextView)convertView.findViewById(R.id.character_adapter_text_view_Name);
        TextView textViewGender = (TextView)convertView.findViewById(R.id.character_adapter_text_view_gender);

        textViewGender.setText(character.getName());
        textViewName.setText(character.getName());

        return convertView;


    }
}
