package com.felipe.labs.countryapi.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.apache.commons.io.FilenameUtils
import java.io.*
import kotlin.concurrent.thread

object ImageSvg {
    private val mClient = OkHttpClient()

    fun loadUrlIntoImageView(url: String, imageView: ImageView) {
        val cacheBitmap =
            loadBitmap(imageView.context.filesDir.path, FilenameUtils.getName(url))

        if (cacheBitmap != null) {
//            withContext(Dispatchers.Main) {
            imageView.setImageBitmap(cacheBitmap)
//            }
        }

        val request = Request.Builder()
            .url(url)
            .build()
        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttp", e.message, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) return

                val svg = response.body!!.string()

                CoroutineScope(Dispatchers.IO).launch() {
                    writeImageFile(
                        imageView.context.filesDir.path,
                        FilenameUtils.getName(url),
                        svg
                    )
                    imageView.context.filesDir
                    val pic = SVG.getFromString(svg).renderToPicture()
                    val pd = PictureDrawable(pic)
                    val imageWidth = Math.min(imageView.width, pd.intrinsicWidth)
                    var imageHeight = Math.min(imageView.height, pd.intrinsicHeight)
                    if (imageWidth < pd.intrinsicWidth) {
                        val ratio = imageWidth.toFloat() / pd.intrinsicWidth.toFloat()
                        val newWight = pd.intrinsicHeight.toFloat() * ratio
                        imageHeight = newWight.toInt()
                    }
                    Log.i("Image", "W: $imageWidth  H: $imageHeight")
                    val bitmap = Bitmap.createBitmap(
                        pd.intrinsicWidth,
                        pd.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    canvas.drawPicture(pd.picture)
                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }

        })
    }

    fun saveBitmap(baseDir: String, fileName: String, bitmap: Bitmap) {
        val thread = thread {
            val sp = File.separatorChar
            val directory = baseDir + sp + "flags"
            val fl = File(directory)
            if (!fl.exists()) {
                fl.mkdir()
            }
            val fullName = directory + sp + fileName
            val fos = FileOutputStream(fullName)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//            fw.write(content)
            fos.flush()
            fos.close()
        }
        thread.join()

    }

    fun loadBitmap(baseDir: String, fileName: String): Bitmap? {
        val sp = File.separatorChar
        val directory = baseDir + sp + "flags"
        val fullName = directory + sp + fileName
        val fl = File(fullName)
        if (!fl.exists()) {
            return null
        }
        val lastUpdate = fl.lastModified()
        
        val fw = FileReader(fullName)
        val svg = fw.readText()

        val pic = SVG.getFromString(svg).renderToPicture()
        val pd = PictureDrawable(pic)
        val bitmap =
            Bitmap.createBitmap(pd.intrinsicWidth, pd.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawPicture(pd.picture)
        fw.close()
        return bitmap


    }

    private fun writeImageFile(baseDir: String, fileName: String, content: String) {
        val thread = thread {
            val sp = File.separatorChar
            val directory = baseDir + sp + "flags"
            val fl = File(directory)
            if (!fl.exists()) {
                fl.mkdir()
            }
            val fullName = directory + sp + fileName
            val fw = FileWriter(fullName)
            fw.write(content)
            fw.flush()
            fw.close()
        }
        thread.join()
    }

}