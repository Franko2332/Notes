package ru.gb.notes.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.gb.notes.interfaces.ExitFromNotesController;

public class ExitDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Exit from Notes").setMessage("Do you really want to leave?")
                .setCancelable(true).
                setNegativeButton("NO",
                        (dialogInterface, i) -> {
                            ((ExitFromNotesController) requireActivity()).cancel();
                        })
                .setPositiveButton("YES",
                        (dialogInterface, i) -> {
                            ((ExitFromNotesController) requireActivity()).exit();
                        });
        return builder.show();
    }
}
