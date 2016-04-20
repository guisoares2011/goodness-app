package imabi.no.kyo.com.goodness;

import android.annotation.TargetApi;
 import android.content.Intent;
 import android.os.Build;
 import android.support.v4.app.FragmentActivity;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.TextView;
 import android.widget.Toast;

 import imabi.no.kyo.com.goodness.DAO.AchievementsDAO;
 import imabi.no.kyo.com.goodness.fragments.DialogAlert;
 import imabi.no.kyo.com.goodness.fragments.DialogConfirmation;
 import imabi.no.kyo.com.goodness.helpers.SettingsGetter;
 import imabi.no.kyo.com.goodness.objects.Achievement;

public class MainActivity extends FragmentActivity {

     AchievementsDAO achievementsDAO;

     @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         //TODO: Implement Count, getAllAchivements(), getCurrentSugestion(),
         //TODO: Define a new kind of layout
         //TODO: Implements Event time to register a create intent notification

         this.achievementsDAO = new AchievementsDAO(this);
         achievementsDAO.resetAll();
         final SettingsGetter settings = new SettingsGetter(getBaseContext());
         Button showAchivements = (Button) this.findViewById(R.id.show_achivements_button);

         showAchivements.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent i = new Intent(MainActivity.this, ListaBoasAcoes.class);
                 startActivity(i);
             }
         });
         //Intent i = new Intent(MainActivity.this, SettingsActivity.class);
         //startActivity(i);

         //Setting a new if has sugestion pending
         if (achievementsDAO.hasSuggestionActive()) {
             updateLayoutWithCurrentSugestion();
         }


         // prepare intent which is triggered if the
         // notification is selected
         //
         //        Intent intent = new Intent(this, SplashScreenActivity.class);
         //        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
         //        Notification n  = new Notification.Builder(this)
         //                .setContentTitle("New mail from " + "test@gmail.com")
         //                .setContentText("Subject")
         //                .setSmallIcon(R.drawable.abc_spinner_textfield_background_material)
         //                .setContentIntent(pIntent)
         //                .setAutoCancel(true)
         //                .addAction(R.drawable.abc_spinner_textfield_background_material, "And more", pIntent).build();
         //
         //        // hide the notification after its selected
         //        n.flags |= Notification.FLAG_AUTO_CANCEL;
         //        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
         //
         //        notificationManager.notify(0, n);
     }

     public void giveSuggestionOrDoneOne(View v) {
         if (achievementsDAO.hasSuggestionActive()) {
             //TODO Implements Modal Confirmation
             DialogConfirmation dialog = new DialogConfirmation(MainActivity.this, "Confirmação", "Você confirma que realizou está boa ação?") {
                 @Override
                 public void ok(View v) {
                     super.ok(v);
                     achievementsDAO.doneCurrentSuggestion();
                     resetLayout();
                 }
             };
             dialog.show();
         } else {
             //TODO Implements Update
             Achievement newSuggestion = achievementsDAO.getNewSuggestion();
             if (newSuggestion == null) {
                 DialogAlert dialog = new DialogAlert(MainActivity.this, "Parabéns", "Você concluiu todas boas ações indicadas por nós. Agora é com você!") {
                     @Override
                     public void ok(View v) {
                         super.ok(v);
                         resetLayout();
                     }
                 };
                 dialog.show();
             } else {
                 updateLayoutWithSuggestion(newSuggestion);
             }
         }
     }

     private void resetLayout() {
         Button giveMeSugestionOrDoneSuggestion = (Button) this.findViewById(R.id.give_me_suggestion_button);
         giveMeSugestionOrDoneSuggestion.setText(getText(R.string.text_button_giveme_sugestions));
         ((TextView) this.findViewById(R.id.title_achievement)).setText("");
         ((TextView) this.findViewById(R.id.description_achievement)).setText("");
     }

     private void updateLayoutWithCurrentSugestion() {
         this.updateLayoutWithSuggestion(achievementsDAO.getCurrentSuggestion());
     }

     private void updateLayoutWithSuggestion(Achievement achievement) {
         Button giveMeSugestionOrDoneSuggestion = (Button) this.findViewById(R.id.give_me_suggestion_button);
         giveMeSugestionOrDoneSuggestion.setText(getText(R.string.text_done_suggestion));
         ((TextView) this.findViewById(R.id.title_achievement)).setText(achievement.getName());
         ((TextView) this.findViewById(R.id.description_achievement)).setText(achievement.getDescription());
     }
 }
