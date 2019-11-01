package br.com.jujuhealth

import br.com.jujuhealth.data.model.TrainingModel

val slowEasy = TrainingModel(
    mode = TrainingModel.Mode.SLOW,
    difficulty = TrainingModel.Difficulty.EASY,
    repetitions = 10,
    contractionDuration = 4000,
    relaxDuration = 4000
)

val slowMedium = TrainingModel(
    mode = TrainingModel.Mode.SLOW,
    difficulty = TrainingModel.Difficulty.MEDIUM,
    repetitions = 10,
    contractionDuration = 6000,
    relaxDuration = 6000
)

val slowHard = TrainingModel(
    mode = TrainingModel.Mode.SLOW,
    difficulty = TrainingModel.Difficulty.HARD,
    repetitions = 10,
    contractionDuration = 10000,
    relaxDuration = 10000
)

val fastEasy = TrainingModel(
    mode = TrainingModel.Mode.FAST,
    difficulty = TrainingModel.Difficulty.EASY,
    repetitions = 3,
    contractionDuration = 1000,
    relaxDuration = 3000
)

val fastMedium = TrainingModel(
    mode = TrainingModel.Mode.FAST,
    difficulty = TrainingModel.Difficulty.MEDIUM,
    repetitions = 10,
    contractionDuration = 2000,
    relaxDuration = 2000
)

val fastHard = TrainingModel(
    mode = TrainingModel.Mode.FAST,
    difficulty = TrainingModel.Difficulty.HARD,
    repetitions = 10,
    contractionDuration = 3000,
    relaxDuration = 6000
)

var activeMode : TrainingModel? = slowEasy.copy()