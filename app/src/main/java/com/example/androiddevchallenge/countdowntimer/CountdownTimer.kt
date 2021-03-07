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
package com.example.androiddevchallenge.countdowntimer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.CountdownViewModel

@Composable
fun CountdownTimer(
    timeLabel: String,
    status: CountdownViewModel.Status,
    onNumButtonClick: (Int) -> Unit,
    onDeleteButtonClick: () -> Unit,
    startTimer: () -> Unit,
    toggleTimer: () -> Unit,
    stopTimer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        TimerDisplay(
            timeLabel,
            status,
            onDeleteButtonClick
        )

        Spacer(modifier = Modifier.padding(16.dp))

        if (status == CountdownViewModel.Status.INPUT) {
            NumPad(
                onNumButtonClick
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (status == CountdownViewModel.Status.INPUT && timeLabel != "00h 00m 00s") {
                Button(onClick = { startTimer() }) {
                    Text("Start")
                }
            }

            if (status != CountdownViewModel.Status.INPUT) {
                ToggleButton(
                    status,
                    toggleTimer
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Button(
                    onClick = {
                        stopTimer()
                    }
                ) {
                    Text("Reset")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCountdownTimerInput() {
    CountdownTimer(
        "00h 00m 00s",
        CountdownViewModel.Status.INPUT,
        {},
        {},
        {},
        {},
        {}
    )
}

@Preview
@Composable
fun PreviewCountdownRunning() {
    CountdownTimer(
        "00h 00m 00s",
        CountdownViewModel.Status.RUNNING,
        {},
        {},
        {},
        {},
        {}
    )
}

/**
 *
 */
@Composable
fun TimerDisplay(
    timeLabel: String,
    status: CountdownViewModel.Status,
    onDeleteButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = timeLabel, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.padding(16.dp))
        if (status == CountdownViewModel.Status.INPUT && timeLabel != "00h 00m 00s") {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Delete",
                    modifier = Modifier.clickable { onDeleteButtonClick() },
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
fun NumPad(
    onNumButtonClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            NumButton(1, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(2, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(3, onNumButtonClick)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            NumButton(4, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(5, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(6, onNumButtonClick)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            NumButton(7, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(8, onNumButtonClick)
            Spacer(modifier = Modifier.padding(8.dp))
            NumButton(9, onNumButtonClick)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            NumButton(0, onNumButtonClick)
        }
    }
}

@Composable
fun NumButton(number: Int, onNumButtonClick: (Int) -> Unit) {
    Button(
        onClick = { onNumButtonClick(number) }
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun ToggleButton(
    status: CountdownViewModel.Status,
    toggleTimer: () -> Unit
) {
    val toggleButtonText = when (status) {
        CountdownViewModel.Status.RUNNING -> {
            "Pause"
        }
        CountdownViewModel.Status.PAUSE -> {
            "Resume"
        }
        else -> {
            ""
        }
    }

    Button(onClick = { toggleTimer() }) {
        Text(toggleButtonText)
    }
}
