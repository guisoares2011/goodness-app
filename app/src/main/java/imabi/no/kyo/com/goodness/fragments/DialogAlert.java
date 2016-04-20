package imabi.no.kyo.com.goodness.fragments;

/**
 * Created by gui-wani on 26/11/2015.
 */
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import imabi.no.kyo.com.goodness.R;


public class DialogAlert extends Dialog {

    public DialogAlert(Context context, String title, String message){
        super(context);
        this.setContentView(R.layout.fragment_custom_alert_dialog);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ((TextView) this.findViewById(R.id.error_title)).setText(title);
        ((TextView) this.findViewById(R.id.message_content)).setText(message);

        Button buttonOK = (Button) this.findViewById(R.id.button_ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok(v);
            }
        });
    }

    public void ok(View v){
        dismiss();
    }

}
