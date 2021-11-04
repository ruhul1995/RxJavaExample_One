package com.example.rxjavaexample_one

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val Tag: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable: Observable<Task>  = Observable // create a new Observable object
                .fromIterable(DataSource.createTasksList()) // apply 'fromIterable' operator
            .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .filter(object : Predicate<Task> { // add this to resolved 5 sec delay
                    override fun test(task: Task): Boolean
                    {
                        Log.d(Tag,"test: " + Thread.currentThread().name)
                        try {
                            // 5 sec delay, so that seekbar is shown 5 sec later in ui // this could be when it takes certain time to show data on UI
                            Thread.sleep(1000)
                        }
                        catch (e:InterruptedException){
                            e.printStackTrace()
                        }
                        return task.isComplete
                    }
                })
            .observeOn(AndroidSchedulers.mainThread()) // designate observer thread (main thread)

        taskObservable.subscribe(object : Observer<Task> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Tag,"onSubscribe: called" )

            }
            override fun onNext(task: Task) {  // run on main thread
                Log.d(Tag,"onNext:" + Thread.currentThread().name)
                Log.d(Tag,"onNext:" + task.description)

            }
            override fun onError(e: Throwable) {
                Log.d(Tag,"onError: " ,e)
            }
            override fun onComplete() {
                Log.d(Tag,"onComplete: called. ")

            }

        })
    }
}