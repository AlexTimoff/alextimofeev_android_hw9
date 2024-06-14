package com.example.alextimofeev_android_hw9.fragments

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.alextimofeev_android_hw9.R
import com.example.alextimofeev_android_hw9.databinding.QuestionnaireViewBinding
import com.example.alextimofeev_android_hw9.quiz.QuizStorage

class QuestionnaireView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): LinearLayout(context, attrs){
    private val binding: QuestionnaireViewBinding
    private var corAnswer = -1
    init {
        val inflatedView = inflate(context, R.layout.questionnaire_view, this)
        binding = QuestionnaireViewBinding.bind(inflatedView)
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            checkChange?.invoke()
        }
    }

    var checkChange: (() -> Unit)?=null

    //Передача данных в RadioGroup
    fun addAllData(data: QuizStorage.QuizQuestion){
            binding.textQuestion.text = data.text
            binding.position1.text = data.answer1
            binding.position2.text = data.answer2
            binding.position3.text = data.answer3
            corAnswer = data.correctAnswer
    }

    //Проверка корректности введенного значения
    fun isCorrectAnswer(): Boolean {
        if (corAnswer==-1) return false
        when (corAnswer) {
          1 -> return binding.position1.isChecked
          2 -> return binding.position2.isChecked
          3 -> return binding.position3.isChecked
        }
        return false
    }

}