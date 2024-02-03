package raa.example.timerscreen.ui

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
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
import kotlin.math.sin


class TimerScreen : Fragment() {

    companion object {
        fun newInstance() = TimerScreen()
    }

    private var _binding: FragmentTimerScreenBinding? = null
    private val binding get() = _binding!!

    val entries = ArrayList<PieEntry>()

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

        entries.add(PieEntry(1f, "Прошло"))
        entries.add(PieEntry(999f, "Осталось"))

        initState()
        binding.addPersonButton.setOnClickListener {

            val container = requireActivity() as? OpenAddPersonFragment
            container?.openFragment(AddPersonFragment.newInstance())

        }

        val rootView = binding.layoutWithImage
        val windowBackground = view.background

        binding.blureView.setupWith(rootView, RenderScriptBlur(this.requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(10f)


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

        // Получаем размеры изображения
        val drawable = imageView.drawable
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        // Рассчитываем максимальные смещения, чтобы не выходить за границы
        val maxTranslateX = imageView.width - drawableWidth
        val maxTranslateY = imageView.height - drawableHeight

        val ovalCenterX = maxTranslateX / 2
        val ovalCenterY = maxTranslateY / 2

        val ovalRadiusX = maxTranslateX / 2f * 0.8f
        val ovalRadiusY = maxTranslateY / 2f * 0.7f

        // Рассчитываем новые координаты с учетом овальной траектории и случайного смещения
        val translateX = (ovalCenterX + ovalRadiusX * cos(fraction)).toFloat()
        val translateY = (ovalCenterY + ovalRadiusY * sin(fraction)).toFloat()


        // Применяем смещение к матрице
        matrix.postTranslate(translateX, translateY)

        // Устанавливаем новую матрицу
        imageView.imageMatrix = matrix
    }


    private fun initState() {
        lifecycleScope.launch {
            viewModel.state
                .transform {
                    emit(it)
                }
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect {
                    when (it) {
                        is Content -> {
                            entries[0] = PieEntry(it.count, "Прошло")
                            entries[1] = PieEntry(1000f - it.count, "Осталось")

                            val dataSet = getPieDataSet(entries)
                            drawPie(PieData(dataSet))
                        }

                        is Loading -> TODO()
                    }
                }
        }
    }


    private fun drawPie(data: PieData) {
        binding.pie.apply {
            this.data = data

            description.isEnabled = false
            setHoleColor(Color.TRANSPARENT) // Прозрачная внутренняя область (дырка)
            holeRadius = 90f
            transparentCircleRadius = 40f
            setTransparentCircleColor(Color.TRANSPARENT)
            setDrawEntryLabels(false) // Не рисовать метки внутри секторов
            legend.isEnabled = false // Не показывать легенду

            setEntryLabelTextSize(14f)
            setEntryLabelColor(Color.WHITE)

            animateY(0)

        }

    }

    private fun getPieDataSet(entries: ArrayList<PieEntry>): PieDataSet {
        val dataSet = PieDataSet(entries, "My Pie Chart")
        dataSet.colors = listOf(
            Color.rgb(148, 87, 235), // Фиолетовый
            Color.rgb(186, 104, 200), // Фиолетово-розовый
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f
        dataSet.sliceSpace = 5f
        dataSet.selectionShift = 10f

        return dataSet
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    interface OpenAddPersonFragment {
        fun openFragment(fragment: Fragment)
    }


}