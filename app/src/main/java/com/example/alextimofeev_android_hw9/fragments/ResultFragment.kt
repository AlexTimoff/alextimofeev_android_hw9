package com.example.alextimofeev_android_hw9.fragments

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.alextimofeev_android_hw9.R
import com.example.alextimofeev_android_hw9.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: ResultFragmentArgs by navArgs()

    // Создаем View для фрагмента из файла разметки.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    //Вызывается системой для уничтожения созданной View фрагмента и освобождения памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showScore()
        addAnimation()

        binding.tryAgain.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }
    }

    private fun showScore(){
        val maxScores = args.possibleScore
        val scoredPoints = args.pointsScored
        val res = "Your result $scoredPoints/$maxScores"
        binding.result.text = res
    }

    //Функция добавления анимаций
    private fun addAnimation(){
        if (args.pointsScored >=2) {
            binding.congrats.visibility = View.VISIBLE
            binding.lottieAnim2.visibility = View.INVISIBLE

            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(binding.congrats, scaleX, scaleY).apply {
                duration = 2000
                interpolator = AccelerateDecelerateInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }.start()

            ObjectAnimator.ofArgb(
                binding.result,
                "textColor",
                Color.parseColor("#81377e"),
                Color.parseColor("#a51b0b")
            ).apply {
                duration = 2000
                interpolator = AccelerateDecelerateInterpolator()
                repeatMode = ObjectAnimator.REVERSE
                repeatCount = ObjectAnimator.INFINITE
            }.start()

        } else{
            binding.lottieAnim.visibility = View.INVISIBLE
            binding.lottieAnim2.visibility = View.VISIBLE

            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f)
            ObjectAnimator.ofPropertyValuesHolder(binding.tryAgain, scaleX, scaleY).apply {
                duration = 2000
                interpolator = AccelerateDecelerateInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }.start()


            ObjectAnimator.ofArgb(
                binding.result,
                "textColor",
                Color.parseColor("#4e1a18"),
                Color.parseColor("#a98783")
            ).apply {
                duration = 2000
                interpolator = AccelerateDecelerateInterpolator()
                repeatMode = ObjectAnimator.REVERSE
                repeatCount = ObjectAnimator.INFINITE
            }.start()

        }


    }


}