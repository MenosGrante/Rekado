package com.pavelrekun.konae

import android.content.Context
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.pavelrekun.konae.adapters.DirectoryAdapter
import com.pavelrekun.konae.filters.ExtensionFileFilter
import com.pavelrekun.konae.utils.StorageUtils
import java.io.File
import java.io.FileFilter
import java.util.*


class Konae : AdapterView.OnItemClickListener {

    private var internal: Boolean = true

    private val fileList = ArrayList<File>()
    private lateinit var currentDir: File
    private lateinit var context: Context
    private lateinit var alertDialog: AlertDialog
    private var filesListView: ListView? = null
    private val adapterSetter: AdapterSetter? = null
    private lateinit var result: Result
    private lateinit var fileFilter: FileFilter
    private lateinit var title: String

    fun with(context: Context): Konae {
        this.context = context

        updateCurrentDir()

        return this
    }

    fun withChosenListener(result: Result): Konae {
        this.result = result
        return this
    }

    fun withFileFilter(fileFilter: ExtensionFileFilter): Konae {
        this.fileFilter = fileFilter
        return this
    }

    fun withTitle(title: String):Konae {
        this.title = title
        return this
    }

    private fun updateCurrentDir() {
        if (internal) {
            this.currentDir = Environment.getExternalStorageDirectory()
        } else {
            this.currentDir = File(StorageUtils.getExternalMemoryPaths(context)?.get(0))
        }
    }

    fun build(): Konae {
        val adapter = refreshDirs()
        adapterSetter?.apply(adapter)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setAdapter(adapter, null)

        builder.setNegativeButton(R.string.dialog_button_negative, null)

        if (StorageUtils.checkExternalStoragePresent(context)) {
            builder.setNeutralButton(if (internal) R.string.dialog_button_storage_external else R.string.dialog_button_storage_internal) { dialog, which ->
                internal = !internal

                updateCurrentDir()
                close()
                build()
                show()
            }
        }

        alertDialog = builder.create()
        filesListView = alertDialog.listView
        filesListView?.onItemClickListener = this
        return this
    }

    fun close() {
        alertDialog.dismiss()
    }

    fun show(): Konae {
        alertDialog.show()
        return this
    }


    private fun refreshDirs(): DirectoryAdapter {
        listDirs()

        val adapter = DirectoryAdapter(context, fileList, R.layout.item_file)

        adapterSetter?.apply(adapter)

        filesListView?.adapter = adapter

        return adapter
    }

    private fun listDirs() {
        fileList.clear()

        // Get files
        val files = currentDir.listFiles(fileFilter)


        if (!StorageUtils.checkExternalStoragePresent(context)) {
            if (currentDir.parent != null && currentDir != Environment.getExternalStorageDirectory()) {
                fileList.add(File(".."))
            }
        } else {
            if (currentDir != File(StorageUtils.getExternalMemoryPaths(context)?.get(0))) {
                fileList.add(File(".."))
            }
        }

        if (files != null) {

            val dirList = LinkedList<File>()
            for (f in files) {
                if (f.isDirectory) {
                    if (!f.name.startsWith(".")) {
                        dirList.add(f)
                    }
                }
            }
            sortByName(dirList)
            fileList.addAll(dirList)

            val fileList = LinkedList<File>()
            for (f in files) {
                if (!f.isDirectory) {
                    if (!f.name.startsWith(".")) {
                        fileList.add(f)
                    }
                }
            }
            sortByName(fileList)
            this.fileList.addAll(fileList)
        }
    }

    private fun sortByName(list: List<File>) {
        Collections.sort(list) { f1, f2 -> f1.name.toLowerCase().compareTo(f2.name.toLowerCase()) }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position < 0 || position >= fileList.size) {
            return
        }

        val file = fileList[position]
        currentDir = if (file.name == "..") {
            currentDir.parentFile
        } else {
            if (file.isDirectory) {
                file
            } else {
                result.onChoosePath(file)
                alertDialog.dismiss()
                return
            }
        }
        refreshDirs()

    }

    interface AdapterSetter {
        fun apply(adapter: DirectoryAdapter)
    }

    interface Result {
        fun onChoosePath(dirFile: File)
    }
}