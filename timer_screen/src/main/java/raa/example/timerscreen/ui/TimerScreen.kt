package raa.example.timerscreen.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import raa.example.timer_screen.databinding.FragmentTimerScreenBinding
import raa.example.timerscreen.Content
import raa.example.timerscreen.Loading
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin


class TimerScreen : Fragment() {

    private var _binding: FragmentTimerScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TimerScreenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initState()
        initBlurView(view.background)
        initBackImageAnimation()

        binding.addPersonButton.setOnClickListener {

            val container = requireActivity() as? OpenAddPersonFragment
            container?.openFragment()

        }

        viewModel.loadData()
    }

    private fun initBlurView(view: Drawable) {
        val rootView = binding.layoutWithImage
        binding.blureView.setupWith(rootView, RenderScriptBlur(this.requireContext()))
            .setFrameClearDrawable(view)
            .setBlurRadius(10f)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initBackImageAnimation() {
        val backgroundImageView = binding.imageView

        val animator = ValueAnimator.ofFloat(0f, 2 * Math.PI.toFloat())
        animator.apply {
            duration = 50000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { valueAnimator ->
                val fraction = valueAnimator.animatedValue as Float
                updateBackgroundPosition(fraction, backgroundImageView)
            }
            start()
        }
    }

    private fun updateBackgroundPosition(fraction: Float, imageView: ImageView) {
        val matrix = Matrix()

        // Размеры изображения
        val drawable = imageView.drawable
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        // Максимальные смещения, чтобы не выходить за границы
        val maxTranslateX = imageView.width - drawableWidth
        val maxTranslateY = imageView.height - drawableHeight

        val ovalCenterX = maxTranslateX / 2
        val ovalCenterY = maxTranslateY / 2

        val ovalRadiusX = maxTranslateX / 2f * 0.8f
        val ovalRadiusY = maxTranslateY / 2f * 0.7f

        // Новые координаты с учетом овальной траектории и случайного смещения
        val translateX = (ovalCenterX + ovalRadiusX * cos(fraction)).toFloat()
        val translateY = (ovalCenterY + ovalRadiusY * sin(fraction)).toFloat()

        matrix.postTranslate(translateX, translateY)

        imageView.imageMatrix = matrix
    }


    @SuppressLint("SetTextI18n")
    private fun initState() {

        viewModel.state.observe(requireActivity()){
            when (it) {
                is Content -> {
                    val dataSet = getPieDataSet(it.entry)
                    Log.e("launch_main_view", dataSet.toString())
                    drawPie(PieData(dataSet), it.a)
                    binding.topText.text = "${it.a.toString().substringBefore('.')}."

                    binding.bottomText.text = "${it.a.toString().substringAfter('.')}%"
                }

                is Loading -> TODO()
            }
        }

    }


    private fun drawPie(data: PieData, count: Float) {
        binding.pie.apply {
            this.data = data

            description.isEnabled = false
            setHoleColor(Color.TRANSPARENT) // Прозрачная внутренняя область (дырка)
            holeRadius = 80f
            transparentCircleRadius = 10f
            setTransparentCircleColor(Color.TRANSPARENT)
            setDrawEntryLabels(false) // Не рисовать метки внутри секторов
            setDrawCenterText(true)
            setEntryLabelColor(Color.TRANSPARENT)
            legend.isEnabled = false // Не показывать легенду
            setNoDataTextColor(Color.TRANSPARENT)
            animateY(0)

        }

    }

    private fun getPieDataSet(entries: ArrayList<PieEntry>): PieDataSet {
        val dataSet = PieDataSet(entries, PIE_DATA_SET_LABLE)
        dataSet.colors = listOf(
            Color.WHITE,
            Color.argb(128,255,255,255)
        )
        //dataSet.valueTextColor = Color.TRANSPARENT
        dataSet.sliceSpace = 0f

        return dataSet
    }

    interface OpenAddPersonFragment {
        fun openFragment()
    }

    companion object {
        private const val PIE_DATA_SET_LABLE = ""

        fun newInstance() = TimerScreen()
    }


}