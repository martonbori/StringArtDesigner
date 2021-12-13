package hu.bme.aut.stringartdesigner.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.DialogFragment
import hu.bme.aut.stringartdesigner.databinding.DialogLoadPatternBinding
import hu.bme.aut.stringartdesigner.databinding.DialogSavePatternBinding

class LoadPatternDialogFragment : DialogFragment(){
    interface LoadPatternDialogListener {
        fun onLoadNameSubmit(name: String)
    }

    private lateinit var listener: LoadPatternDialogListener

    private lateinit var binding: DialogLoadPatternBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? LoadPatternDialogListener
            ?: throw RuntimeException("Activity must implement the LoadPatternDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogLoadPatternBinding.inflate(LayoutInflater.from(context))
        val saveFilesPref = activity?.getSharedPreferences("saves",Context.MODE_PRIVATE)?.all?.values
        val saveFiles: MutableList<String> = mutableListOf()
        if (saveFilesPref != null) {
            for (saveFile in saveFilesPref) {
                saveFiles.add(saveFile.toString())
            }
        }

        binding.loadFile.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            saveFiles
        )

        return AlertDialog.Builder(requireContext())
            .setTitle("LOAD PATTERN")
            .setView(binding.root)
            .setPositiveButton("LOAD") { dialogInterface, i ->
                if (isValid()) {
                    listener.onLoadNameSubmit(getName())
                }
            }
            .setNegativeButton("CANCEL", null)
            .create()
    }

    private fun isValid() = binding.loadFile.isNotEmpty()

    private fun getName() = binding.loadFile.selectedItem.toString()

    companion object {
        const val TAG = "LoadPatternDialogFragment"
    }

}