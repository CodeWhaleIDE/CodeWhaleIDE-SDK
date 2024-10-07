package com.bluewhaleyt.codewhaleide.sdk.testapp.action;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataKeys;
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo;
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionEvent;
import com.bluewhaleyt.codewhaleide.sdk.core.action.ClickAction;
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentationInfo;
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation;

public class MyJavaAction extends ClickAction {
    public MyJavaAction() {
        super("myJavaAction", new ClickActionPresentation(
                new ActionPresentationInfo("My Java Action")
        ));
    }

    @Override
    public void onInitialize(@NonNull ActionEvent event) {
        super.onInitialize(event);
        event.getPresentation().setIconResId(android.R.drawable.ic_input_add);
    }

    @Override
    public void onClick(@NonNull ActionEvent event) {
        Context context = event.getDataContext().get(DataKeys.Context);
        Toast.makeText(context, "hello java", Toast.LENGTH_SHORT).show();
        event.getPresentation().setIconResId(android.R.drawable.ic_delete);
    }
}
