package com.pavelrekun.konae.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.pavelrekun.konae.R
import com.pavelrekun.konae.utils.FileUtils
import java.io.File

class DirectoryAdapter(context: Context, private val fileList: List<File>, resId: Int) : ArrayAdapter<File>(context, resId, R.id.item_file_name, fileList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewGroup = super.getView(position, convertView, parent) as ViewGroup

        val fileName = viewGroup.findViewById<TextView>(R.id.item_file_name)
        val fileSize = viewGroup.findViewById<TextView>(R.id.item_file_size)
        val fileIcon = viewGroup.findViewById<ImageView>(R.id.item_file_icon)

        val file = fileList[position]

        fileName.text = file.name

        if (file.isDirectory) {
            fileIcon.setImageResource(R.drawable.ic_chooser_folder)
            fileSize.visibility = View.GONE
        } else {
            fileIcon.setImageResource(R.drawable.ic_chooser_file)
            fileSize.visibility = View.VISIBLE
            fileSize.text = FileUtils.getReadableFileSize(file.length())
        }

        return viewGroup
    }
}