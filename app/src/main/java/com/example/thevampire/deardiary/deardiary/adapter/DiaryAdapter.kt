package com.example.thevampire.deardiary.deardiary.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.checkbox.isCheckPromptChecked

import com.example.thevampire.deardiary.R
import com.example.thevampire.deardiary.databinding.DiaryTitleLayoutBinding
import com.example.thevampire.deardiary.deardiary.database.DiaryDataBase
import com.example.thevampire.deardiary.deardiary.database.entity.DiaryItem
import com.example.thevampire.deardiary.deardiary.ui.AddDiaryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.diary_title_layout.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.uiThread
import javax.inject.Inject
import kotlin.collections.ArrayList

class DiaryAdapter @Inject constructor(@ApplicationContext val context : Context) : RecyclerView.Adapter<ViewHolder>()
{

    var firebaseFirestore : FirebaseFirestore? = null
    private val diaryList = arrayListOf<DiaryItem>()

    fun setData(temp_list : ArrayList<DiaryItem>){
        Log.d("Firebase","Settings data")
        this.diaryList.clear()
        this.diaryList.addAll(temp_list)
        notifyDataSetChanged()
    }

    fun setFirestore(fire : FirebaseFirestore)
    {
        this.firebaseFirestore = fire
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val diaryTitleLayoutBinding = DiaryTitleLayoutBinding.inflate(parent.context.layoutInflater)
       return ViewHolder(diaryTitleLayoutBinding).apply {
           diaryTitleLayoutBinding.root.setOnClickListener {
               val intent = Intent(parent.context,AddDiaryActivity::class.java)
               intent.putExtra("dairy_did",diaryList[adapterPosition].did)
               parent.context.startActivity(intent)
           }

           diaryTitleLayoutBinding.root.setOnLongClickListener {
               showOptionsMenu(parent.context,adapterPosition)
               true }
       }
    }

    private fun showOptionsMenu(context: Context, position: Int){
        MaterialDialog(context).show {
            title(text = "Delete")
            message(text= "Are you Sure?")
            if(diaryList[position].upload_status == 1)
            {
                checkBoxPrompt(text = "Delete From Cloud?"){}
            }

            positiveButton(text = "Yes"){
                val isChecked = it.isCheckPromptChecked()
                val itemToRemove = diaryList[position]
                removefromDB(itemToRemove)
                removefromDataSet(position)


                if(isChecked)
                {
                    removeFromFirebase(itemToRemove)
                }
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
                it.dismiss()
            }
            negativeButton(text = "Cancel"){
                it.dismiss()
            }

        }
    }

    override fun getItemCount() = diaryList.size


    override fun onBindViewHolder(holder : ViewHolder, position: Int) {

        val item = diaryList[position]
        holder.binding.apply {
            diaryTitle.text = item.title
            dateTextview.text = item.date

            if(item.upload_status == 1)
                cloudUploadStatusImg.setBackgroundResource(R.drawable.ic_cloud_upload_geen_24dp)
            else
                cloudUploadStatusImg.setBackgroundResource(R.drawable.ic_cloud_upload_red_24dp)
        }


    }

    private fun removeFromFirebase(item : DiaryItem) {
        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email
        val post = firebaseFirestore?.collection("users/$email/posts")?.document("${item.title} ${item.did}")
        post?.delete()?.addOnSuccessListener {
            Toast.makeText(context,"Deleted From Firebase",Toast.LENGTH_SHORT).show()
        }
    }

    fun removefromDataSet( i : Int)
    {

        diaryList.removeAt(i)
        notifyItemRemoved(i)
        notifyItemRangeChanged(i,diaryList.size)
    }

    fun removefromDB(item : DiaryItem)
    {
//        doAsync {
//            val i = DiaryDataBase.getInstance(context)?.getDao()?.delete(item)
//            uiThread {
//
//            }
//        }
    }

}

class ViewHolder(val binding: DiaryTitleLayoutBinding ) : RecyclerView.ViewHolder(binding.root)
