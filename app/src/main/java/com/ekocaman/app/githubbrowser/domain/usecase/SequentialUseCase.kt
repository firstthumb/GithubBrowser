package com.ekocaman.app.githubbrowser.domain.usecase

import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

abstract class SequentialUseCase<in PARAM, RESPONSE> protected constructor(private val schedulerProvider: SchedulerProvider) : Disposable {
    private val compositeDisposable = CompositeDisposable()

    fun execute(onSuccess: Consumer<RESPONSE>, onError: Consumer<Throwable>, param: PARAM): Disposable {
        val disposable = buildUseCaseSingle(param)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
        return this
    }

    protected abstract fun buildUseCaseSingle(param: PARAM): Single<RESPONSE>

    override fun dispose() {
        compositeDisposable.clear()
    }

    override fun isDisposed() = compositeDisposable.isDisposed
}