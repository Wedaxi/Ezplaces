package com.places.compose.usecase

import kotlinx.coroutines.*

abstract class UseCase<Params, Result> {

    abstract suspend fun run(params: Params): Result

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        onResult: (Result) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }
}