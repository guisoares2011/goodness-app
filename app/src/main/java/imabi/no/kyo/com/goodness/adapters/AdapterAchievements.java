package imabi.no.kyo.com.goodness.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import imabi.no.kyo.com.goodness.R;
import imabi.no.kyo.com.goodness.objects.Achievement;

/**
 * Created by gui-wani on 26/11/2015.
 */
public class AdapterAchievements extends BaseAdapter {
    private final List<Achievement> achievementList;
    private Activity activity;

    public AdapterAchievements(Activity activity, List<Achievement> alunos) {
        this.achievementList = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return achievementList.size();
    }

    @Override
    public Object getItem(int posicao) {
        return achievementList.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return achievementList.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item, null);
        Achievement achievement = achievementList.get(posicao);
        ((TextView) view.findViewById(R.id.name)).setText(achievement.getName());
        return view;
    }
}
