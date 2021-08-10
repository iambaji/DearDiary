package com.example.thevampire.deardiary.deardiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.example.thevampire.deardiary.deardiary.adapters.DiaryAdapter
import com.example.thevampire.deardiary.R
import com.example.thevampire.deardiary.databinding.ActivityFeedBinding
import com.example.thevampire.deardiary.databinding.NavHeaderLayoutBinding
import com.example.thevampire.deardiary.deardiary.persistance.database.entity.DiaryItem
import com.example.thevampire.deardiary.deardiary.ui.viewmodels.FeedViewModel
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_diary_body.*
import kotlinx.android.synthetic.main.activity_feed.*
import org.jetbrains.anko.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_layout.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {


    @Inject  lateinit var diaryAdapter : DiaryAdapter

    private lateinit var mdrawerToggle : ActionBarDrawerToggle

    lateinit var binding: ActivityFeedBinding

    private val viewModel : FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.feedToolbar.ltoolbar)
        binding.feedToolbar.ltoolbar.title = "Notes"
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        setUpUI()
        setUpObservers()
    }


    private fun setUpObservers(){


        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                when(it){
                    is FeedViewModel.FeedUIState.NotesSuccess ->{
                        diaryAdapter.setData(ArrayList(it.notes))
                    }
                    is FeedViewModel.FeedUIState.Error ->{
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setUpUI(){
        with(binding){

             mdrawerToggle =  object : ActionBarDrawerToggle(this@FeedActivity,mdrawerLayout,R.string.draweropen,R.string.drawerclose){

            }

            mdrawerToggle.syncState()

            mdrawerLayout.addDrawerListener(mdrawerToggle as ActionBarDrawerToggle)
            val nav_header = navigationview.getHeaderView(0)
            val navHeaderLayoutBinding = NavHeaderLayoutBinding.inflate(layoutInflater)
            navHeaderLayoutBinding.displaynameNav.text = viewModel.getUsername()

            navigationview.setNavigationItemSelectedListener {
                it.isChecked = true
                val msg = it.title
                when(it.itemId)
                {
                    R.id.aboutitem ->{
                        mdrawerLayout?.closeDrawer(GravityCompat.START)
                        // startActivity(Intent(this@FeedActivity,AboutActivity::class.java))
                        MaterialDialog(this@FeedActivity).title(text = "About the App")
                            .message(res = R.string.about_app,
                            ).positiveButton(text = "Okay").show()
                    }

                    R.id.additem ->
                    {
                        mdrawerLayout?.closeDrawer(GravityCompat.START)
                        startActivity(Intent(this@FeedActivity,AddDiaryActivity::class.java))
                    }



                    R.id.logoutitem ->{
                        mdrawerLayout?.closeDrawer(GravityCompat.START)
                        Toast.makeText(this@FeedActivity,"Signing out",Toast.LENGTH_SHORT).show()
                        viewModel.logoutUser()
                        finish()
                    }
                }

                true
            }



            diaryAdapter?.setFirestore(FirebaseFirestore.getInstance())
            feedRecyclerView.layoutManager = LinearLayoutManager(this@FeedActivity)
            feedRecyclerView.adapter = diaryAdapter
            viewModel.getNotes()

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feed_activity_toolbar,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        val f = mdrawerToggle?.onOptionsItemSelected(item) as Boolean
        if(f)
        {
            binding.mdrawerLayout?.openDrawer(GravityCompat.START)
            return true
        }
        else{
            when(item?.itemId)
            {
                R.id.add_new ->{
                    startActivity(Intent(this,AddDiaryActivity::class.java))

                }

                R.id.signout_feed_layoult ->{
                    viewModel.logoutUser()
                }

                R.id.upload ->{
                    //syncToCloud(diary_list)
                    sendToServer()
                }

                R.id.download -> viewModel.downloadFromServer()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun saveToLocalDatabase( list : ArrayList<DiaryItem>)
    {
//        val db = DiaryDataBase.getInstance(this@FeedActivity)
//        val newitems = ArrayList<DiaryItem>()
//        for(listitem in list)
//        {
//            if(!localitems.contains(listitem))
//            {
//                Log.d("Firebase","Adding New Item ${listitem.title}")
//                listitem.did = null
//                newitems.add(listitem)
//            }
//
//
//        }
//
//        doAsync {
//
//            db?.getDao()?.addAll(*newitems.toTypedArray())
//            uiThread {
//                toast("${newitems.size} Posts are Saved In Database!")
//            }
//        }

    }

    fun sendToServer()
    {
//        val firestore = FirebaseFirestore.getInstance()
//        val email = firebase_auth.currentUser?.email
//        val batch = firestore.batch()
//        val items = ArrayList<DiaryItem>()
//        localitems.forEach{
//            if(it.upload_status ==0)
//                items.add(it)
//        }
//
//        items.forEach {
//           val setop = firestore.collection("users/$email/posts")?.document("${it.title} ${it.did}")
//            batch.set(setop,it)
//        }
//
//        batch.commit().addOnSuccessListener {
//            items.forEach{
//                it.upload_status =1
//
//                doAsync {
//                    val db = DiaryDataBase.getInstance(this@FeedActivity)
//                    db?.getDao()?.updateUploadStatus(it)
//                }
//            }
//            diaryadapter?.notifyDataSetChanged()
//            toast("Upload Complete!")
//        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        mdrawerToggle?.syncState()
    }



}

