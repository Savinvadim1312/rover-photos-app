package com.example.martian.rover_list.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.martian.databinding.RoverBinding
import android.widget.ImageView
import androidx.navigation.findNavController
import com.example.martian.R
import com.example.martian.rover_list.data.RoverModel
import com.squareup.picasso.Picasso




class Rover : ConstraintLayout {

    var mBinding: RoverBinding

    var roverModel: RoverModel? = null
        set(value) {
            field = value
            mBinding.rover = value

            renderImage()
        }

    var screenWidth: Int = 300
        set(value) {
            field = value
            var root: ConstraintLayout = findViewById<ConstraintLayout>(R.id.root)
            val layoutParams = root.getLayoutParams()
            layoutParams.width = value
            root.setLayoutParams(layoutParams)
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = DataBindingUtil.inflate(inflater, R.layout.rover, this, true)

        mBinding.button.setOnClickListener{view: View ->
            view.findNavController().navigate(
                RoversListFragmentDirections.actionRoversListToRoverPhotos(
                    roverModel?.name ?: ""
                )
            )
        }
    }

    fun renderImage() {

        val images: HashMap<Int, String> = HashMap()

        images.put(5, "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/sn-curiosity.jpg?itok=ok6vgedp")
        images.put(7, "https://www.papercraftsquare.com/wp-content/uploads/2016/09/Spirit-Robotic-Rover-Paper-Model.jpg")
        images.put(6, "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/sn-curiosity.jpg?itok=ok6vgedp")

//        TODO use databinding
        var imageView: ImageView = findViewById(R.id.imageView)
        Picasso.get().load(images.get(roverModel?.id)).into(imageView)
    }

}