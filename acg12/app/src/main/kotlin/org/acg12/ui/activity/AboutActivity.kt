package org.acg12.ui.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View

import org.acg12.config.Constant
import org.acg12.listener.ItemClickSupport
import org.acg12.ui.base.PresenterActivityImpl
import org.acg12.views.AboutView

class AboutActivity : PresenterActivityImpl<AboutView>(), View.OnClickListener {

    override fun created(savedInstance: Bundle) {
        super.created(savedInstance)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == Constant.TOOLBAR_ID) {
            finish()
        }
    }

}
