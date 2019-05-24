package com.example.moiuchastkoviy.ui.main

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.ui.main.statement.StatementFragment
import com.example.moiuchastkoviy.ui.main.categories.CategoryFragment
import com.example.moiuchastkoviy.ui.main.contacts.ContactsFragment
import com.example.moiuchastkoviy.ui.main.about_us.AboutUsFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.fragment_category.*


class MainActivity : AppCompatActivity(), View.OnClickListener {


    companion object{
        val CATEGORY: String = CategoryFragment::class.java.simpleName
        val CONTACTS: String = ContactsFragment::class.java.simpleName
        val ARTICLE: String = StatementFragment::class.java.simpleName
        val FAQFRAGMENT: String = AboutUsFragment::class.java.simpleName

        const val REQUEST_PHONE_CALL = 1
    }

    private var currentSelectedItem: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cut -> {

//                text_view.text = "Cut"
                return true
            }
            R.id.action_copy -> {
//                text_view.text = "Copy"
                return true
            }
            R.id.action_paste -> {
//                text_view.text = "Paste"
                return true
            }
            R.id.action_new -> {
//                text_view.text = "New"
                return true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun initViews(){
        articles.setOnClickListener(this)
        station.setOnClickListener(this)
        statement.setOnClickListener(this)
        app_info.setOnClickListener(this)
        fab.setOnClickListener(this)

        articles.performClick()
//        languare.setOnClickListener{
//                        // Initialize a new instance of
//            val builder = AlertDialog.Builder(this@MainActivity)
//            builder.setTitle("Ты да ТЫ!!!")
//            builder.setMessage("Сделать что то?")
//            builder.setPositiveButton("ДА"){dialog, which ->
//
//                Toast.makeText(applicationContext,"Конюшня",Toast.LENGTH_SHORT).show()
//            }
//
//            builder.setNegativeButton("НЕТ"){dialog,which ->
//                Toast.makeText(applicationContext,"не Конюшня",Toast.LENGTH_SHORT).show()
//            }
//
//            builder.setNeutralButton("ПОф"){_,_ ->
//                Toast.makeText(applicationContext,"послать все в (_0_)",Toast.LENGTH_SHORT).show()
//            }
//
//            val dialog: AlertDialog = builder.create()
//            dialog.show()
//        }
    }

    override fun onClick(v: View?) {

        if (v== null) return

        if (currentSelectedItem!= null && currentSelectedItem!!.id == v.id) return

        when (v.id) {
            articles.id -> {

                loadFragment(CATEGORY)
                onItemSelected(articles)
            }
            station.id -> {
                loadFragment(CONTACTS)
                onItemSelected(station)
            }
            statement.id -> {
                loadFragment(ARTICLE)
                onItemSelected(statement)
            }
            app_info.id -> {
                loadFragment(FAQFRAGMENT)
                onItemSelected(app_info)
            }
            fab.id -> {
                phoneCall102()
            }
        }

    }

    private fun onItemSelected(newItem: TextView) {
        if (currentSelectedItem!= null)
            unSelectItem(currentSelectedItem!!)
        selectItem(newItem)
        currentSelectedItem = newItem
    }

    private fun selectItem(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.bottom_nav_selected))
        setTextViewDrawableColor(textView, R.color.bottom_nav_selected)

    }

    private fun unSelectItem(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.bottom_nav_unselected))
        setTextViewDrawableColor(textView, R.color.bottom_nav_unselected)
    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(ContextCompat.getColor(textView.context, color), PorterDuff.Mode.SRC_IN)
            }
        }
    }
    private fun loadFragment(tag: String) {

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val currentFragment = fragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }


        var fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            when (tag) {
                CATEGORY -> fragment = CategoryFragment()
                CONTACTS -> fragment = ContactsFragment()
                ARTICLE -> fragment = StatementFragment()
                FAQFRAGMENT -> fragment = AboutUsFragment()
            }
            transaction.add(R.id.nav_host_container, fragment!!, tag)

        } else {
            transaction.show(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commitAllowingStateLoss()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_PHONE_CALL -> phoneCall102()
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


    private fun phoneCall102() {
//        ACTION_СAll - для прямого звонка
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:102"))
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.CALL_PHONE),1)
        }
        else
        {
            startActivity(intent)
        }
    }
}
