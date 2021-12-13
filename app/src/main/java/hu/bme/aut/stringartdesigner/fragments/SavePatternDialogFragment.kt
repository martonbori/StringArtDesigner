package hu.bme.aut.stringartdesigner.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.stringartdesigner.databinding.DialogSavePatternBinding

class SavePatternDialogFragment : DialogFragment(){
    interface SavePatternDialogListener {
        fun onSaveNameSubmit(name: String)
    }

    private lateinit var listener: SavePatternDialogListener

    private lateinit var binding: DialogSavePatternBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? SavePatternDialogListener
            ?: throw RuntimeException("Activity must implement the SavePatternDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSavePatternBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle("SAVE PATTERN")
            .setView(binding.root)
            .setPositiveButton("SAVE") { dialogInterface, i ->
                if (isValid()) {
                    listener.onSaveNameSubmit(getName())
                }
            }
            .setNegativeButton("CANCEL", null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getName() = binding.etName.text.toString()

    companion object {
        const val TAG = "SavePatternDialogFragment"
    }

}