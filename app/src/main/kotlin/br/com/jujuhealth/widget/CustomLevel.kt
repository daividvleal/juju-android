package br.com.jujuhealth.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import br.com.jujuhealth.R
import kotlinx.android.synthetic.main.level_custom.view.*

class CustomLevel(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private lateinit var bgReference: Drawable
    private lateinit var bgSmallReference: Drawable
    private lateinit var bgMediumReference: Drawable
    private lateinit var bgHighReference: Drawable
    private var labelColor: Int = 0
    private lateinit var labelReference: String

    init {
        View.inflate(context, R.layout.level_custom, this)

        context.withStyledAttributes(
            attrs,
            R.styleable.CustomLevel,
            0,
            0
        ) {
            bgReference = getDrawable(R.styleable.CustomLevel_bg)!!
            bgSmallReference = getDrawable(R.styleable.CustomLevel_bg_easy)!!
            bgMediumReference = getDrawable(R.styleable.CustomLevel_bg_medium)!!
            bgHighReference = getDrawable(R.styleable.CustomLevel_bg_high)!!
            labelColor = getColor(R.styleable.CustomLevel_label_color, 0)
            labelReference = getString(R.styleable.CustomLevel_label)!!

            bg.setImageDrawable(bgReference)
            lvl_1.setImageDrawable(bgSmallReference)
            lvl_2.setImageDrawable(bgMediumReference)
            lvl_3.setImageDrawable(bgHighReference)
            level_txt.setTextColor(labelColor)
            level_txt.text = labelReference
        }
    }

    private fun setSelectedLevel(level: LEVEL){
        bg.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
        level_txt.setTextColor(resources.getColor(R.color.darkGray))
        when(level){
            LEVEL.EASY ->{
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_gray))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_gray))
                level_txt.text = context.getString(R.string.level_easy)
            }
            LEVEL.MEDIUM -> {
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_gray))
                level_txt.text = context.getString(R.string.level_medium)
            }
            LEVEL.HARD -> {
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
                level_txt.text = context.getString(R.string.level_hard)
            }
        }
    }

    private fun setUnselectedLevel(level: LEVEL){
        bg.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_dark))
        level_txt.setTextColor(resources.getColor(android.R.color.white))
        when(level){
            LEVEL.EASY ->{
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_soft_pink))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_soft_pink))
                level_txt.text = context.getString(R.string.level_easy)
            }
            LEVEL.MEDIUM -> {
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_soft_pink))
                level_txt.text = context.getString(R.string.level_medium)
            }
            LEVEL.HARD -> {
                lvl_1.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                lvl_2.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                lvl_3.setImageDrawable(context.getDrawable(R.drawable.background_item_filter_light))
                level_txt.text = context.getString(R.string.level_hard)
            }
        }
    }

    companion object{
        fun setSelectedLevel(
            viewSelected: CustomLevel, levelSelected: LEVEL,
            unselectedView1: CustomLevel, unselectedLevel1: LEVEL,
            unselectedView2: CustomLevel, unselectedLevel2: LEVEL){
            viewSelected.setSelectedLevel(levelSelected)
            unselectedView1.setUnselectedLevel(unselectedLevel1)
            unselectedView2.setUnselectedLevel(unselectedLevel2)
        }
    }


    enum class LEVEL {
        EASY, MEDIUM, HARD
    }
}