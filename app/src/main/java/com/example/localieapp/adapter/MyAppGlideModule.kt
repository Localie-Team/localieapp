package com.example.localieapp.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

@GlideModule
class MyAppGlideModule : AppGlideModule() {

        // This is where you register components that Glide should know about.
        override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
        ) {
                // The FirebaseImageLoader takes care of downloading the image data from Firebase Storage
                // and passing it to Glide for display.
        registry.append(
        StorageReference::class.java,
        InputStream::class.java,
        FirebaseImageLoader.Factory()
        )
        }
        }