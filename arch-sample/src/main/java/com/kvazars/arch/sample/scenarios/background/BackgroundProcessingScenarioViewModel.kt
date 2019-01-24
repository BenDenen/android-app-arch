package com.kvazars.arch.sample.scenarios.background

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import com.kvazars.arch.core.LibViewModel
import kotlin.random.Random

class BackgroundProcessingScenarioViewModel : LibViewModel() {

    val inProgress = State(false)
    val primeIndex = State<Int>()
    val primeNumber = State<Optional<Long>>()
    val calculateAction = Action<Unit>()

    init {
        calculateAction.listenUntilDestroy {
            primeIndex.set((Random.nextInt(8000, 12000)))
            inProgress.set(true)
            primeNumber.set(None)

            doInBackground(
                task = {
                    nthPrime(primeIndex.value)
                },
                onSuccess = { result ->
                    inProgress.set(false)
                    primeNumber.set(result.toOptional())
                }
            )
        }
    }

    private fun nthPrime(n: Int): Long {
        fun isPrime(n: Long): Boolean {
            for (i in 2 until n) {
                if (n % i == 0L) {
                    return false
                }
            }
            return true
        }

        var candidate = 2L
        var count = 0
        while (count < n) {
            if (isPrime(candidate)) {
                ++count
            }
            ++candidate
        }
        return candidate - 1
    }
}
