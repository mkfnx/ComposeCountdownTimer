package com.example.androiddevchallenge.countdowntimer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        modifier = Modifier.fillMaxSize()
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

            Button(onClick = {
                stopTimer()
            }) {
                Text("Reset")
            }
        }
    }
}

@Preview
@Composable
fun PreviewCountdownTimer() {
    CountdownTimer("00h 00m 00s", CountdownViewModel.Status.INPUT, {}, {}, {}, {}, {})
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
    Row {
        Text(text = timeLabel)
        Spacer(modifier = Modifier.padding(16.dp))
        if (status == CountdownViewModel.Status.INPUT && timeLabel != "00h 00m 00s") {
            Text(text = "Delete", Modifier.clickable { onDeleteButtonClick() })
        }
    }
}

@Composable
fun NumPad(
    onNumButtonClick: (Int) -> Unit,
) {
    Column {
        Row {
            NumButton(1, onNumButtonClick)
            NumButton(2, onNumButtonClick)
            NumButton(3, onNumButtonClick)
        }

        Row {
            NumButton(4, onNumButtonClick)
            NumButton(5, onNumButtonClick)
            NumButton(6, onNumButtonClick)
        }

        Row {
            NumButton(7, onNumButtonClick)
            NumButton(8, onNumButtonClick)
            NumButton(9, onNumButtonClick)
        }

        NumButton(0, onNumButtonClick)
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
    lateinit var toggleButtonText: String

    if (status == CountdownViewModel.Status.RUNNING) {
        toggleButtonText = "Pause"
    } else if (status == CountdownViewModel.Status.PAUSE) {
        toggleButtonText = "Resume"
    }

    Button(onClick = { toggleTimer() }) {
        Text(toggleButtonText)
    }
}