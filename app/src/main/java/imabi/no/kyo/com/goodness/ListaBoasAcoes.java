package imabi.no.kyo.com.goodness;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuItem;
import android.widget.ListView;

import imabi.no.kyo.com.goodness.DAO.AchievementsDAO;
import imabi.no.kyo.com.goodness.adapters.AdapterAchievements;
import imabi.no.kyo.com.goodness.objects.Achievement;


public class ListaBoasAcoes extends FragmentActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_lista_boas_acoes);
         final ListView listaAlunos = (ListView) findViewById(R.id.list_achievements);
         AchievementsDAO dao = new AchievementsDAO(this);
         dao.close();
         AdapterAchievements adapter = new AdapterAchievements(this, dao.getAchievements());
         listaAlunos.setAdapter(adapter);
     }
 }
