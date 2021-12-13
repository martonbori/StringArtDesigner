package hu.bme.aut.stringartdesigner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.AlertDialog
import hu.bme.aut.stringartdesigner.databinding.ActivityMainBinding
import hu.bme.aut.stringartdesigner.fragments.*
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.sqlite.PersistentDataHelper

class MainActivity : AppCompatActivity(), UIFragment.IPatternChanged {

    lateinit var binding : ActivityMainBinding
    lateinit var canvasFragment: CanvasFragment
    private lateinit var dataHelper: PersistentDataHelper
    private var menuListeners: MutableList<IMenuEventListener> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        canvasFragment = supportFragmentManager.findFragmentById(R.id.canvas_fragment) as CanvasFragment
        setContentView(binding.root)

        dataHelper = PersistentDataHelper(this)
        dataHelper.open()
        restorePersistedObjects()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val fileMenu: Menu = binding.toolbar.menu
        menuInflater.inflate(R.menu.file_menu, fileMenu)
        for (i in 0 until fileMenu.size()) {
            val menuItem: MenuItem = fileMenu.getItem(i)
            menuItem.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
            if (menuItem.hasSubMenu()) {
                val subMenu: SubMenu = menuItem.subMenu
                for (j in 0 until subMenu.size()) {
                    subMenu.getItem(j)
                        .setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
                }
            }
        }
        return super.onCreateOptionsMenu(fileMenu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                savePersistentObjects()
                savePattern()
                true
            }
            R.id.menu_load -> {
                restorePersistedObjects()
                loadPattern()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePattern() {
        AlertDialog.Builder(this)
            .setMessage(R.string.are_you_sure_want_to_exit)
            .setPositiveButton(R.string.ok) { _, _ -> onExit() }
            .setNegativeButton(R.string.cancel, null)
            .show()

        for(listener in menuListeners) {
            listener.savePattern("save")
        }
    }

    private fun loadPattern() {
        for(listener in menuListeners) {
            listener.loadPattern("load")
            updateCanvas()
        }
    }


    override fun updateCanvas() {
        canvasFragment.update()
    }

    override fun animatePattern() {
        canvasFragment.animatePattern()
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
        dataHelper.open()
        dataHelper.persistPolygon(Pattern.polygon)
        dataHelper.persistPoints(Pattern.points.values.toList())
        dataHelper.persistLines(Pattern.lines)
        dataHelper.persistPlusLines(Pattern.plusLines)
        dataHelper.close()
    }

    private fun restorePersistedObjects() {
        dataHelper.open()
        Pattern.restoreObjects(dataHelper.restorePolygon(), dataHelper.restorePoints(), dataHelper.restoreLines(), dataHelper.restorePlusLines())
        dataHelper.close()
        updateCanvas()

    }

    fun addMenuListener(listener: IMenuEventListener) {
        menuListeners.add(listener)
    }
    fun removeMenuListener(listener: IMenuEventListener) {
        menuListeners.remove(listener)
    }

    interface IMenuEventListener {
        fun savePattern(name:String)
        fun loadPattern(name:String)
    }

}