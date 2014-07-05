package com.antonioleiva.recycledviewextensions.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.antonioleiva.recycledviewextensions.BaseLayoutManager;

public class OrientationDialogFragment extends DialogFragment {

    private int mSelectedOrientation;

    public interface Listener {
        void onNewOrientationSelected(int orientation);
    }

    public static OrientationDialogFragment newInstance(int initialOrientation) {
        Bundle args = new Bundle();
        args.putInt("initialOrientation", initialOrientation);
        OrientationDialogFragment dlg = new OrientationDialogFragment();
        dlg.setArguments(args);
        return dlg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedOrientation = getArguments().getInt("initialOrientation");
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.action_orientation)
                .setSingleChoiceItems(new String[]{"Horizontal", "Vertical"}, mSelectedOrientation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSelectedOrientation = i;
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getActivity() instanceof Listener) {
                            ((Listener) getActivity()).onNewOrientationSelected(mSelectedOrientation);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}
