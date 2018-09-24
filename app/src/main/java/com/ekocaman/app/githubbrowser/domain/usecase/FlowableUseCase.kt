package com.ekocaman.app.githubbrowser.domain.usecase

import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.processors.PublishProcessor
import timber.log.Timber

abstract class FlowableUseCase<in PARAM, RESPONSE> protected constructor(private val schedulerProvider: SchedulerProvider) : Disposable {
    private val compositeDisposable = CompositeDisposable()
    private val processor = PublishProcessor.create<PARAM>()

    fun execute(onSuccess: Consumer<RESPONSE>, onError: Consumer<Throwable>): Disposable {
        val disposable = processor
                .switchMap {
                    buildUseCaseFlowable(it)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onSuccess, onError, Action {
                    Timber.v("Completed use case")
                })

        compositeDisposable.add(disposable)
        return this
    }

    protected abstract fun buildUseCaseFlowable(param: PARAM): Flowable<RESPONSE>

    fun sendEvent(param: PARAM) {
        processor.onNext(param)
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

    override fun isDisposed() = compositeDisposable.isDisposed
}