/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class CountdownViewModel : ViewModel() {
    private val secondsInHour = TimeUnit.HOURS.toSeconds(1)
    private val secondsInMinute = TimeUnit.MINUTES.toSeconds(1)

    enum class Status {
        INPUT, RUNNING, PAUSE, FINISHED
    }

    companion object {
        private const val INPUT_MAX_SIZE = 6
    }

    var remainingTime by mutableStateOf(0L)
    var timerLabel by mutableStateOf("00h 00m 00s")
    var status by mutableStateOf(Status.INPUT)
    private var timerInputs = mutableListOf<Int>()
    private val timeMultipliers = arrayOf(
        1,
        10,
        secondsInMinute,
        secondsInMinute * 10,
        secondsInHour,
        secondsInHour * 10,
    )

    private var countDownTimer: CountDownTimer? = null

    private fun formatTime(): String {
        if (status == Status.INPUT) {
            val offset = INPUT_MAX_SIZE - timerInputs.size
            val timeComponents = mutableListOf<Int>()

            for (i in 0 until INPUT_MAX_SIZE) {
                if (i < offset) {
                    timeComponents.add(0)
                } else {
                    timeComponents.add(timerInputs[i - offset])
                }
            }

            return "%d%dh %d%dm %d%ds".format(
                timeComponents[0],
                timeComponents[1],
                timeComponents[2],
                timeComponents[3],
                timeComponents[4],
                timeComponents[5],
            )
        } else {
            var time = remainingTime
            var hours = 0L
            var minutes = 0L

            if (time > 0) {
                hours = time / secondsInHour
                time -= hours * secondsInHour
            }

            if (time > 0) {
                minutes = time / secondsInMinute
                time -= minutes * secondsInMinute
            }

            return "%02dh %02dm %02ds".format(hours, minutes, time)
        }
    }

    fun onNumPadButtonClicked(number: Int) {
        if (timerInputs.size == 0 && number == 0) {
            return
        }
        else if (timerInputs.size < 6) {
            timerInputs.add(number)

            timerLabel = formatTime()
        }
    }

    fun onDeleteButtonClicked() {
        if (timerInputs.isNotEmpty()) {
            timerInputs.removeLast()

            timerLabel = formatTime()
        }
    }

    fun startTimer() {
        status = Status.RUNNING
        remainingTime = calculateTimeFromInput()

        countDownTimer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(remainingTime),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(p0: Long) {
                remainingTime -= 1
                timerLabel = formatTime()
                Log.d("countdown", timerLabel)
            }

            override fun onFinish() {
                resetTimer()
            }
        }

        countDownTimer?.start()
        status = Status.RUNNING
    }

    fun toggleTimer() {
        status = if (status == Status.RUNNING) {
            countDownTimer?.cancel()
            Status.PAUSE
        } else {
            countDownTimer?.start()
            Status.RUNNING
        }
    }

    fun resetTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
        remainingTime = 0L
        timerLabel = "00h 00m 00s"
        timerInputs = mutableListOf()
        status = Status.INPUT
    }

    private fun calculateTimeFromInput(): Long {
        var totalTime = 0L

        for (i in timerInputs.indices) {
            val input = timerInputs[i]
            if (input != 0) {
                totalTime += input * timeMultipliers[timerInputs.size - i - 1]
            }
        }

        return totalTime
    }
}
