package com.example.thevampire.deardiary.deardiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.thevampire.deardiary.R
import com.example.thevampire.deardiary.databinding.ActivityAddDiaryBinding

import com.example.thevampire.deardiary.deardiary.persistance.database.entity.DiaryItem
import com.example.thevampire.deardiary.deardiary.ui.viewmodels.AddDiaryBodyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_diary_body.*
import java.util.*

@AndroidEntryPoint
class AddDiaryActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddDiaryBinding

    val viewModel : AddDiaryBodyViewModel by viewModels()

    lateinit var menu : Menu
    var areWeEditing = false
    var diaryItem : DiaryItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.mytoolbar.ltoolbar)
        binding.mytoolbar.ltoolbar.title = "Add Note"

        setUpIntent()
    }

    private fun setUpIntent()
    {
        val did = intent?.extras?.get("dairy_did")
        did?.let {
            areWeEditing = true
            val item = viewModel.getDiaryBody(it as Int)
            diaryItem = item
            supportActionBar?.title = item.title
            binding.editData.setText(item.body)
            binding.editTitle.setText(item.title)

            binding.editData.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(binding.editData.text.toString() != item?.body)
                        menu?.findItem(R.id.saveitem)?.title = "Update"
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.saveitem)
        {
            if(areWeEditing){
                diaryItem?.body = binding.editData.text.toString()
                diaryItem?.title = binding.editTitle.text.toString()
                diaryItem?.let { viewModel.updateNote(it) }
            }else{
                var title = binding.editTitle.text.toString()
                var body = binding.editData.text.toString()
                viewModel.addNote(title,body)
            }
            val intent = Intent(this@AddDiaryActivity,FeedActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        return true
    }
    }



