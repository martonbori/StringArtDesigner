package hu.bme.aut.stringartdesigner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import hu.bme.aut.stringartdesigner.databinding.ActivityMainBinding
import hu.bme.aut.stringartdesigner.fragments.*
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.sqlite.DbConstants
import hu.bme.aut.stringartdesigner.sqlite.PersistentDataHelper

class MainActivity : AppCompatActivity(), UIFragment.IPatternChanged {

    lateinit var binding : ActivityMainBinding
    lateinit var canvasFragment: CanvasFragment
    private lateinit var dataHelper: PersistentDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        canvasFragment = supportFragmentManager.findFragmentById(R.id.canvas_fragment) as CanvasFragment
        setContentView(binding.root)

        dataHelper = PersistentDataHelper(this)
        dataHelper.open()
        restorePersistedObjects()
    }

    override fun updateCanvas() {
        canvasFragment.update()
    }

    override fun onResume() {
        super.onResume()
        dataHelper.open()
    }

    override fun onPause() {
        dataHelper.close()
        super.onPause()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.are_you_sure_want_to_exit)
            .setPositiveButton(R.string.ok) { _, _ -> onExit() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun onExit() {
        savePersistentObjects()
        finish()
    }

    private fun savePersistentObjects() {
        dataHelper.persistPolygon(Pattern.polygon)
        dataHelper.persistPoints(Pattern.points.values.toList())
        dataHelper.persistLines(Pattern.lines)
        dataHelper.persistPlusLines(Pattern.plusLines)
        dataHelper.close()
    }

    private fun restorePersistedObjects() {
        Pattern.restoreObjects(dataHelper.restorePolygon(), dataHelper.restorePoints(), dataHelper.restoreLines(), dataHelper.restorePlusLines())
        updateCanvas()

    }

}