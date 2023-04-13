package com.example.compose_dome.skin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * Context收集器
 */
class ContextCollector : Application.ActivityLifecycleCallbacks {
    companion object {
        val allContext = HashSet<WeakReference<Context>>()

        /**
         * 注册野生的Context
         */
        fun registerWildContext(ctx: Context) {
            unregisterContext(ctx)
            allContext.add(WeakReference(ctx))
        }

        /**
         * 移除context
         */
        fun unregisterContext(ctx: Context) {
            for (weakCtx in allContext) {
                if (weakCtx.get() == ctx) {
                    allContext.remove(weakCtx)
                    break
                }
            }
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        allContext.add(WeakReference(p0))
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        unregisterContext(p0)
    }
}
