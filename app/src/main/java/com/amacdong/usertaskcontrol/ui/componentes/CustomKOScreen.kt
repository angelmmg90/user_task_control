package com.amacdong.usertaskcontrol.ui.componentes


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.ui.componentes.interfaces.OnCustomDialogLoading
import com.amacdong.usertaskcontrol.ui.componentes.interfaces.OnCustomKOScreen
import kotlinx.android.synthetic.main.custom_dialog_loading.view.*

class CustomKOScreen : RelativeLayout, OnCustomDialogLoading, OnCustomKOScreen {

    private var customDialogContainer: RelativeLayout? = null
    private var onCustomDialogLoading: OnCustomDialogLoading? = null
    private val errorNotDefinedValue = -1

    constructor(context: Context) : super(context) {
        LayoutInflater.from(context).inflate(R.layout.custom_dialog_loading, this, true)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.setupAtributes(attrs)

        LayoutInflater.from(context).inflate(R.layout.custom_dialog_loading, this, true)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.custom_dialog_loading, this, true)
        init()
        setupAtributes(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.custom_dialog_loading, this, true)
        init()
        this.setupAtributes(attrs)
    }


    private fun init() {
        if (context is OnCustomDialogLoading) {
            onCustomDialogLoading = context as OnCustomDialogLoading
        }
    }

    override val isShowingCustomDialogLoading: Boolean
        get() = customDialogContainer != null && customDialogContainer?.visibility == View.VISIBLE;

    override fun showSpinner() {
        customDialogContainer?.visibility = View.VISIBLE
        this.setBackgroundColor(resources.getColor(R.color.transparent))
        imgStatus.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        tvStatus.visibility = View.GONE
    }

    override fun dismissSpinner() {
        customDialogContainer?.visibility = View.GONE
        tvStatus.visibility = View.GONE
    }

    override fun hideComponent() {
        customDialogContainer?.visibility = View.GONE
        this.setBackgroundColor(resources.getColor(R.color.transparent))
        tvStatus.visibility = View.GONE
    }

    override fun onShowKOScreen(
        imgName: String,
        imgHeight: Int,
        imgWidth: Int,
        errorMessage: String,
        sizeTextError: Float
    ) {

        dismissSpinner()

        this.visibility = View.VISIBLE

        this.setBackgroundColor(resources.getColor(R.color.white))

        val imgMeasures = imgWidth > 0 && imgHeight > 0

        val sizeCustomText = sizeTextError > 0

        val layoutParams = imgStatus.layoutParams as RelativeLayout.LayoutParams

        if (imgMeasures) {
            val height =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imgHeight.toFloat(), resources.displayMetrics)
                    .toInt()
            val width =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imgWidth.toFloat(), resources.displayMetrics)
                    .toInt()
            layoutParams.width = width
            layoutParams.height = height
        } else {
            layoutParams.width = 350
            layoutParams.height = 350
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }

        if (sizeCustomText) {
            tvStatus.textSize = sizeTextError
        }

        imgStatus.scaleType = ImageView.ScaleType.FIT_CENTER
        imgStatus.layoutParams = layoutParams


        val withImage = imgName != null && !imgName.isEmpty()

        if (withImage) {
            val imgId = resources.getIdentifier(imgName, "drawable", context.packageName)

            if (imgId != 0) {
                imgStatus.setImageDrawable(context.resources.getDrawable(imgId))
            } else {
                imgStatus.setImageDrawable(context.resources.getDrawable(R.drawable.no_network_connection))
            }
        } else {
            imgStatus.setImageDrawable(context.resources.getDrawable(R.drawable.no_network_connection))
        }


        val messageError =
            if (errorMessage != null && !errorMessage.isEmpty()) errorMessage else context.resources.getString(R.string.error_no_network_connection)
        customDialogContainer?.visibility = View.VISIBLE
        imgStatus.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        tvStatus.setText(messageError)
        tvStatus.visibility = View.VISIBLE
        customDialogContainer?.setBackgroundColor(-0x1)

    }

    override fun onRestoreDefaultScreen() {
        hideComponent()
    }

    override fun onShowKOScreen() {
        onShowKOScreen("", 0, 0, "", 0f)
    }


    @SuppressLint("Recycle")
    private fun setupAtributes(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomKOScreen, 0, 0)
        try {
            val currentColor = ta.getString(R.styleable.CustomKOScreen_changeColor)
            val srcImageToLoad = ta.getString(R.styleable.CustomKOScreen_src_image_to_load)
            val widthImageToLoad = ta.getInteger(R.styleable.CustomKOScreen_status_image_width, errorNotDefinedValue)
            val heightImageToLoad = ta.getInteger(R.styleable.CustomKOScreen_status_image_height, errorNotDefinedValue)

        } catch (ex: Exception) {

        }

    }

}